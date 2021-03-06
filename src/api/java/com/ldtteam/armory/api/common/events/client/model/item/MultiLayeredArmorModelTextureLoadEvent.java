package com.ldtteam.armory.api.common.events.client.model.item;

import com.ldtteam.armory.api.client.model.deserializers.MultiLayeredArmorModelDeserializer;
import com.ldtteam.armory.api.client.model.deserializers.definition.MultiLayeredArmorModelDefinition;
import com.ldtteam.armory.api.common.armor.IMultiComponentArmor;
import com.ldtteam.armory.api.util.references.ModLogger;
import com.ldtteam.smithscore.common.events.SmithsCoreEvent;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marc on 07.12.2015.
 */
public class MultiLayeredArmorModelTextureLoadEvent extends SmithsCoreEvent {

    @Nonnull private final IMultiComponentArmor armor;
    @Nonnull List<MultiLayeredArmorModelDefinition> additionalTextureDefinitions = new ArrayList<>();

    public MultiLayeredArmorModelTextureLoadEvent (IMultiComponentArmor armor) {
        this.armor = armor;
    }

    @Nonnull
    public IMultiComponentArmor getArmor () {
        return armor;
    }

    @Nonnull
    public List<MultiLayeredArmorModelDefinition> getAdditionalTextureLayers() {
        return additionalTextureDefinitions;
    }

    public void addAdditionalTextureLayers(@NotNull ResourceLocation modelDefinitionLocation) {
        try {
            additionalTextureDefinitions.add(MultiLayeredArmorModelDeserializer.instance.deserialize(modelDefinitionLocation));
        } catch (Exception ex) {
            ModLogger.getInstance().error("Error while attempting to add: " + modelDefinitionLocation.toString() + " to the model definition of: " + armor.getRegistryName().toString());
        }
    }
}
