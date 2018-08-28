package com.ldtteam.armory.api.client.model;

/**
 * Enum describing parts of a Biped Model that can be used to render it in the World.
 */
public enum ModelPart
{
    HEAD("Head"),
    HEADWEAR("HeadWear"),
    BODY("Body"),
    ARMLEFT("ArmLeft"),
    ARMRIGHT("ArmRight"),
    LEGRIGHT("LegLeft"),
    LEGLEFT("LegRight");

    private String id;

    ModelPart(String id)
    {
        this.id = id;
    }

    private String getId()
    {
        return id;
    }

    public static final ModelPart getById(String id)
    {
        for(ModelPart part : values())
        {
            if (part.getId().equals(id))
            {
                return part;
            }
        }

        return BODY;
    }
}
