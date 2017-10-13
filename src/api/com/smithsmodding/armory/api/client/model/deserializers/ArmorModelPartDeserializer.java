package com.smithsmodding.armory.api.client.model.deserializers;

import com.google.common.base.Charsets;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableMap;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.smithsmodding.armory.api.client.model.ModelPart;
import com.smithsmodding.armory.api.client.model.deserializers.definition.ArmorModelLayerDefinition;
import com.smithsmodding.armory.api.client.model.deserializers.definition.ArmorModelLayerPartDefinition;
import com.smithsmodding.armory.api.client.model.deserializers.definition.ArmorModelPartDefinition;
import com.smithsmodding.armory.api.util.references.ModLogger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ForgeBlockStateV1;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Class used to deserialize a {@link ArmorModelPartDefinition}
 */
@SideOnly(Side.CLIENT)
public class ArmorModelPartDeserializer implements JsonDeserializer<ArmorModelPartDefinition>
{
    @Nonnull
    public static final           ArmorModelPartDeserializer  instance        = new ArmorModelPartDeserializer();
    @Nonnull private static final Type                        partType       = new TypeToken<ArmorModelPartDefinition>(){}.getType();
    @Nonnull private static final Type                        layerType = new TypeToken<ArmorModelLayerDefinition>() {}.getType();
    @Nonnull private static final Gson                        gson           = new GsonBuilder().registerTypeAdapter(partType, instance).create();

    @Nonnull private final Cache<ResourceLocation, ArmorModelLayerDefinition> cache = CacheBuilder.newBuilder()
                                                                                        .maximumSize(100)
                                                                                        .build();

    /**
     * Method to deserialize a {@link ArmorModelLayerPartDefinition} from a given resource location.
     * @param modelLocation The {@link ResourceLocation} to load the {@link ArmorModelLayerPartDefinition} from.
     * @return The deserialized {@link ArmorModelLayerPartDefinition} stored in the given {@link ResourceLocation}.
     * @throws IOException Thrown when {@link ResourceLocation} does not point to a proper definition.
     */
    @Nonnull
    public ArmorModelPartDefinition deserialize(ResourceLocation modelLocation) throws IOException
    {
        @Nonnull IResource
          iresource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(modelLocation.getResourceDomain(), modelLocation.getResourcePath() + ".json"));
        @Nonnull Reader reader = new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8);

        return gson.fromJson(reader, partType);
    }

    /**
     * Gson invokes this call-back method during deserialization when it encounters a field of the
     * specified type.
     * <p>In the implementation of this call-back method, you should consider invoking
     * {@link JsonDeserializationContext#deserialize(JsonElement, Type)} method to create objects
     * for any non-trivial field of the returned object. However, you should never invoke it on the
     * the same type passing {@code json} since that will cause an infinite loop (Gson will call your
     * call-back method again).
     *
     * @param json    The Json data being deserialized
     * @param typeOfT The type of the Object to deserialize to
     * @return a deserialized object of the specified type typeOfT which is a subclass of {@code T}
     *
     * @throws JsonParseException if json is not in the expected format of {@code typeofT}
     */
    @Override
    public ArmorModelPartDefinition deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement base = jsonObject.get("base");
        JsonObject layers = jsonObject.get("layers").getAsJsonObject();
        JsonObject broken = jsonObject.get("broken").getAsJsonObject();
        ModelPart type = ModelPart.getById(jsonObject.get("type").getAsString());

        ArmorModelLayerDefinition baseLayer;
        ImmutableMap.Builder<ResourceLocation, ArmorModelLayerDefinition> layersLocations = ImmutableMap.builder();
        ImmutableMap.Builder<ResourceLocation, ArmorModelLayerDefinition> brokenLocations = ImmutableMap.builder();

        baseLayer = parseLayerInternal(base, context);

        for (Map.Entry<String, JsonElement> stringJsonElementEntry : layers.entrySet())
        {
            parseJsonLayer(stringJsonElementEntry, layersLocations, context);
        }

        for (Map.Entry<String, JsonElement> entry : broken.entrySet())
        {
            parseJsonLayer(entry, brokenLocations, context);
        }

        return new ArmorModelPartDefinition(baseLayer, layersLocations.build(), brokenLocations.build(), type, jsonObject.has("transform") ? ForgeBlockStateV1.TRSRDeserializer.INSTANCE.deserialize(jsonObject.get("transform"), typeOfT, context) : TRSRTransformation.identity());
    }

    private void parseJsonLayer(@Nonnull Map.Entry<String, JsonElement> keyElementPair, @Nonnull ImmutableMap.Builder<ResourceLocation, ArmorModelLayerDefinition> target, @Nonnull JsonDeserializationContext context) {
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
            catch (Exception e)
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
