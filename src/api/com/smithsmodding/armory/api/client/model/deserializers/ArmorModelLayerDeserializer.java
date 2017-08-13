package com.smithsmodding.armory.api.client.model.deserializers;

import com.google.common.base.Charsets;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.smithsmodding.armory.api.client.model.deserializers.definition.ArmorModelLayerDefinition;
import com.smithsmodding.armory.api.client.model.deserializers.definition.ArmorModelLayerPartDefinition;
import com.smithsmodding.armory.api.util.references.ModLogger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Deserializer for an individual Layer of a Armor.
 * Each layer can be made up out of several different parts with Textures and Transforms
 */
public class ArmorModelLayerDeserializer implements JsonDeserializer<ArmorModelLayerDefinition>
{
    @Nonnull public static final ArmorModelLayerDeserializer instance = new ArmorModelLayerDeserializer();
    @Nonnull private static final Type partType = new TypeToken<ArmorModelLayerPartDefinition>(){}.getType();
    @Nonnull private static final Type definitionType = new TypeToken<ArmorModelLayerDefinition>(){}.getType();
    @Nonnull private static final Gson
      gson = new GsonBuilder().registerTypeAdapter(definitionType, instance).create();

    @Nonnull public final Cache<ResourceLocation, ArmorModelLayerDefinition> cache = CacheBuilder.newBuilder()
                                                                                        .maximumSize(100)
                                                                                        .build();

    /**
     * Method to deserialize a {@link ArmorModelLayerPartDefinition} from a given resource location.
     * @param modelLocation The {@link ResourceLocation} to load the {@link ArmorModelLayerPartDefinition} from.
     * @return The deserialized {@link ArmorModelLayerPartDefinition} stored in the given {@link ResourceLocation}.
     * @throws IOException Thrown when {@link ResourceLocation} does not point to a proper definition.
     */
    @Nonnull
    public ArmorModelLayerDefinition deserialize(ResourceLocation modelLocation) throws IOException, ExecutionException
    {
        ArmorModelLayerDefinition result = cache.get(modelLocation, () -> {
            @Nonnull IResource iresource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(modelLocation.getResourceDomain(), modelLocation.getResourcePath() + ".json"));
            @Nonnull Reader reader = new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8);
            return gson.fromJson(reader, definitionType);
        });

        return result;
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
    @Nonnull
    @Override
    public ArmorModelLayerDefinition deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
    {
        @Nonnull final List<ArmorModelLayerPartDefinition> parts = Lists.newArrayList();
        @Nonnull final JsonObject data = json.getAsJsonObject();
        if (data.has("override"))
        {
            JsonObject overrideObject = data.getAsJsonObject("override");
            ResourceLocation parentLocation = new ResourceLocation(overrideObject.get("parent").getAsString());
            ArmorModelLayerDefinition parentDef = null;
            try
            {
                parentDef = cache.get(parentLocation, () -> deserialize(parentLocation));
            }
            catch (ExecutionException e)
            {
                ModLogger.getInstance().error("Failed to load: " + parentLocation + " as parent armor layer from cache. Attempting none cached route.");
                ModLogger.getInstance().error(e);

                try {
                    parentDef = deserialize(parentLocation);
                    cache.put(parentLocation, parentDef);
                }
                catch (Exception e1)
                {
                    ModLogger.getInstance().error("Also failed to load: " + parentLocation + " as parent armor layer directly. Loading failed!.");
                    ModLogger.getInstance().error(e);
                }
            }

            if (parentDef != null)
            {
                ImmutableMap.Builder<ResourceLocation, ResourceLocation> overrideBuilder = ImmutableMap.builder();

                JsonObject textures = overrideObject.get("textures").getAsJsonObject();
                for(Map.Entry<String, JsonElement> entry : textures.entrySet())
                {
                    overrideBuilder.put(new ResourceLocation(entry.getKey()), new ResourceLocation(entry.getValue().getAsString()));
                }

                parts.addAll(parentDef.createWithOverride(overrideBuilder.build()).getParts());
            }
        }

        if (data.has("parts")) {
            @Nonnull final JsonArray jsonParts = data.getAsJsonArray("parts");
            for (JsonElement element : jsonParts)
            {
                if (element.isJsonPrimitive())
                {
                    try
                    {
                        parts.add(ArmorModelLayerPartDeserializer.instance.deserialize(new ResourceLocation(element.getAsString())));
                    }
                    catch (IOException e)
                    {
                        ModLogger.getInstance().warn("Found JSON element: " + element.toString() + " that does not reference a proper File.");
                        ModLogger.getInstance().warn(e);
                    }
                }
                else if (element.isJsonObject())
                {
                    try
                    {
                        parts.add(ArmorModelLayerPartDeserializer.instance.deserialize(element, partType, context));
                    }
                    catch (JsonParseException e)
                    {
                        ModLogger.getInstance().warn("Found JSON element: " + element.toString() + " that cannot be parsed into a model Part, parsing failed.");
                        ModLogger.getInstance().warn(e);
                    }
                }
                else
                {
                    ModLogger.getInstance().warn("Found JSON element: " + element.toString() + " that cannot be parsed into a model Part, cause it is of an unknown json type.");
                }
            }
        }

        return new ArmorModelLayerDefinition(parts);
    }

}
