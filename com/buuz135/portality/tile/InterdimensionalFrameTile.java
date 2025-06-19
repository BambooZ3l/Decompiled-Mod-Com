/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.block.BasicTileBlock
 *  javax.annotation.Nonnull
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.registries.RegistryObject
 */
package com.buuz135.portality.tile;

import com.buuz135.portality.proxy.CommonProxy;
import com.buuz135.portality.tile.FrameTile;
import com.hrznstudio.titanium.block.BasicTileBlock;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

public class InterdimensionalFrameTile
extends FrameTile<InterdimensionalFrameTile> {
    public InterdimensionalFrameTile(BlockPos pos, BlockState state) {
        super((BasicTileBlock)((RegistryObject)CommonProxy.BLOCK_INTERDIMENSIONAL_MODULE.getLeft()).get(), (BlockEntityType)((RegistryObject)CommonProxy.BLOCK_INTERDIMENSIONAL_MODULE.getRight()).get(), pos, state);
    }

    @Nonnull
    public InterdimensionalFrameTile getSelf() {
        return this;
    }
}

