/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 */
package com.buuz135.portality.block.module;

import com.buuz135.portality.tile.ControllerTile;
import net.minecraft.core.BlockPos;

public interface IPortalModule {
    public void work(ControllerTile var1, BlockPos var2);

    public boolean allowsInterdimensionalTravel();
}

