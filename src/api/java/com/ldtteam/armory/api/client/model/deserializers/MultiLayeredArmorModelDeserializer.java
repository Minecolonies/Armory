package com.ldtteam.armory.api.client.model.deserializers;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.ldtteam.armory.api.client.model.deserializers.definition.ArmorModelPartDefinition;
import com.ldtteam.armory.api.client.model.deserializers.definition.MultiLayeredArmorModelDefinition;
import com.ldtteam.smithscore.util.client.ModelHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Author Marc (Created on: 28.05.2016)
 */
public class MultiLayeredArmorModelDeserializer implements JsonDeserializer<MultiLayeredArmorModelDefinition> {
    public static final MultiLayeredArmorModelDeserializer instance = new MultiLayeredArmorModelDeserializer();

    private static final Type partType = new TypeToken<ArmorModelPartDefinition>() {
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
        if(!modelLocation.getResourcePath().startsWith("models" + File.separator + "item" + File.separator))
        {
            modelLocation = new ResourceLocation(modelLocation.getResourceDomain(), "models" + File.separator + "item" + File.separator + modelLocation.getResourcePath());
        }

        IResource iresource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(modelLocation.getResourceDomain(), modelLocation.getResourcePath() + ".json"));
        Reader reader = new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8);

        return gson.fromJson(reader, definitionType);
    }

    @NotNull
    @Override
    public MultiLayeredArmorModelDefinition deserialize(@NotNull JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonObject partsData = jsonObject.get("parts").getAsJsonObject();

        ImmutableMap.Builder<ResourceLocation, ArmorModelPartDefinition> partsBuilder = ImmutableMap.builder();

        for(Map.Entry<String, JsonElement> partIdData : partsData.entrySet())
        {
            if (partIdData.getValue().isJsonPrimitive())
            {
                try
                {
                    partsBuilder.put(new ResourceLocation(partIdData.getKey()), ArmorModelPartDeserializer.instance.deserialize(new ResourceLocation(partIdData.getValue().getAsString())));
                }
                catch (Exception e)
                {
                    throw new JsonParseException("Failed to deserialize Parts", e);
                }
            }
            else
            {
                partsBuilder.put(new ResourceLocation(partIdData.getKey()), ArmorModelPartDeserializer.instance.deserialize(partIdData.getValue(), partType, context));
            }
        }


        return new MultiLayeredArmorModelDefinition(partsBuilder.build(), ModelHelper.TransformDeserializer.INSTANCE.deserialize(json, typeOfT, context));
    }


}
