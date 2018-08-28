package com.ldtteam.armory.api.client.model.deserializers;

import com.google.common.base.Charsets;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.ldtteam.armory.api.client.model.deserializers.definition.ArmorModelLayerPartDefinition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ForgeBlockStateV1;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;

/**
 * A JSON Deserializer for an part of the model of a Armor.
 */
public class ArmorModelLayerPartDeserializer implements JsonDeserializer<ArmorModelLayerPartDefinition>
{
    @Nonnull public static final ArmorModelLayerPartDeserializer instance = new ArmorModelLayerPartDeserializer();

    @Nonnull private static final Type TRSTType = new TypeToken<TRSRTransformation>() {}.getType();
    @Nonnull private static final Type definitionType = new TypeToken<ArmorModelLayerPartDefinition>(){}.getType();

    @Nonnull private static final Gson gson = new GsonBuilder().registerTypeAdapter(definitionType, instance).registerTypeAdapter(TRSTType,ForgeBlockStateV1.TRSRDeserializer.INSTANCE).create();

    /**
     * Method to deserialize a {@link ArmorModelLayerPartDefinition} from a given resource location.
     * @param modelLocation The {@link ResourceLocation} to load the {@link ArmorModelLayerPartDefinition} from.
     * @return The deserialized {@link ArmorModelLayerPartDefinition} stored in the given {@link ResourceLocation}.
     * @throws IOException Thrown when {@link ResourceLocation} does not point to a proper definition.
     */
    @Nonnull
    public ArmorModelLayerPartDefinition deserialize(ResourceLocation modelLocation) throws IOException
    {
        @Nonnull IResource iresource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(modelLocation.getResourceDomain(), modelLocation.getResourcePath() + ".json"));
        @Nonnull Reader reader = new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8);

        return gson.fromJson(reader, definitionType);
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
    public ArmorModelLayerPartDefinition deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
    {
        @Nonnull final JsonObject jsonObject = json.getAsJsonObject();
        @Nonnull final ResourceLocation id = new ResourceLocation(jsonObject.get("id").getAsString());
        @Nonnull final ResourceLocation location = new ResourceLocation(jsonObject.get("texture").getAsString());
        @Nonnull final TRSRTransformation transformation = ForgeBlockStateV1.TRSRDeserializer.INSTANCE.deserialize(jsonObject.get("transformation"), TRSTType, context);

        return new ArmorModelLayerPartDefinition(id, location, transformation);
    }
}
