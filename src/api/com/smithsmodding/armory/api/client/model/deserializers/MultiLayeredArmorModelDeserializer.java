package com.smithsmodding.armory.api.client.model.deserializers;

import com.google.common.base.Charsets;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.smithsmodding.armory.api.client.model.deserializers.definition.ArmorModelLayerDefinition;
import com.smithsmodding.armory.api.client.model.deserializers.definition.MultiLayeredArmorModelDefinition;
import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.smithscore.util.client.ModelHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Author Marc (Created on: 28.05.2016)
 */
public class MultiLayeredArmorModelDeserializer implements JsonDeserializer<MultiLayeredArmorModelDefinition> {
    public static final MultiLayeredArmorModelDeserializer instance = new MultiLayeredArmorModelDeserializer();

    private static final Type layerType = new TypeToken<ArmorModelLayerDefinition>() {
    }.getType();
    private static final Type definitionType = new TypeToken<MultiLayeredArmorModelDefinition>() {
    }.getType();
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(definitionType, instance).create();

    private MultiLayeredArmorModelDeserializer() {
    }

    /**
     * Method deserializes the given ModelLocation  into a MultiComponentModel.
     * The returned definition will hold all the SubModels in a Map.
     *
     * @param modelLocation The location to load the Definition From.
     * @return A ModelDefinition for a MultiComponentModel.
     * @throws IOException Thrown when the given ModelLocation points to nothing or not to a ModelFile.
     */
    public MultiLayeredArmorModelDefinition deserialize(@NotNull ResourceLocation modelLocation) throws IOException {
        IResource iresource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(modelLocation.getResourceDomain(), modelLocation.getResourcePath() + ".json"));
        Reader reader = new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8);

        return gson.fromJson(reader, definitionType);
    }

    @NotNull
    @Override
    public MultiLayeredArmorModelDefinition deserialize(@NotNull JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement base = jsonObject.get("base");
        JsonObject layers = jsonObject.get("layers").getAsJsonObject();
        JsonObject broken = jsonObject.get("broken").getAsJsonObject();

        ArmorModelLayerDefinition baseLayer;
        HashMap<ResourceLocation, ArmorModelLayerDefinition> layersLocations = new HashMap<>();
        HashMap<ResourceLocation, ArmorModelLayerDefinition> brokenLocations = new HashMap<>();

        baseLayer = parseLayerInternal(base, context);

        layers.entrySet().forEach((Map.Entry<String, JsonElement> entry) -> {
            parseJsonLayer(entry, layersLocations, context);
        });

        broken.entrySet().forEach((Map.Entry<String, JsonElement> entry) -> {
            parseJsonLayer(entry, brokenLocations, context);
        });

        return new MultiLayeredArmorModelDefinition(baseLayer, layersLocations, brokenLocations, ModelHelper.TransformDeserializer.INSTANCE.deserialize(json, typeOfT, context));
    }

    private void parseJsonLayer(@Nonnull Map.Entry<String, JsonElement> keyElementPair, @Nonnull Map<ResourceLocation, ArmorModelLayerDefinition> target, @Nonnull JsonDeserializationContext context) {
        target.put(new ResourceLocation(keyElementPair.getKey()), parseLayerInternal(keyElementPair.getValue(), context));
    }

    @Nonnull
    private ArmorModelLayerDefinition parseLayerInternal(JsonElement layerElement, JsonDeserializationContext context) {
        if (layerElement.isJsonPrimitive())
        {
            try
            {
                return ArmorModelLayerDeserializer.instance.deserialize(new ResourceLocation(layerElement.getAsString()));
            }
            catch (IOException e)
            {
                ModLogger.getInstance().warn("Failed to deserialize the Layer.");
                ModLogger.getInstance().warn(e);
            }
        }
        else
        {
            try
            {
                return ArmorModelLayerDeserializer.instance.deserialize(layerElement, layerType, context);
            }
            catch (JsonParseException e)
            {
                ModLogger.getInstance().warn("Failed to deserialize the Layer.");
                ModLogger.getInstance().warn(e);
            }
        }

        throw new IllegalStateException("Deserialization of armor Failed.");
    }
}
