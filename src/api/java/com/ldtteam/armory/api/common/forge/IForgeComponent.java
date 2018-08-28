/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.ldtteam.armory.api.common.forge;

import com.ldtteam.smithscore.util.common.positioning.Coordinate3D;

public interface IForgeComponent {

    float getPositiveInflunce();

    float getNegativeInfluece();

    int getMaxTempInfluence();

    boolean canInfluenceTE(Coordinate3D coordinate);
}
