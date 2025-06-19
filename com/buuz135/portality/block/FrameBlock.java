/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.block.RotatableBlock
 *  com.hrznstudio.titanium.block.RotatableBlock$RotationType
 *  javax.annotation.Nonnull
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType$BlockEntitySupplier
 *  net.minecraft.world.level.block.state.BlockBehaviour
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 */
package com.buuz135.portality.block;

import com.buuz135.portality.Portality;
import com.buuz135.portality.tile.BasicFrameTile;
import com.buuz135.portality.tile.ControllerTile;
import com.buuz135.portality.tile.FrameTile;
import com.hrznstudio.titanium.block.RotatableBlock;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class FrameBlock<T extends FrameTile<T>>
extends RotatableBlock<T> {
    public FrameBlock(String name, Class<T> tileClass) {
        super(name, BlockBehaviour.Properties.m_60926_((BlockBehaviour)Blocks.f_50075_), tileClass);
        this.setItemGroup(Portality.TAB);
    }

    public void m_6810_(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        BlockEntity controller;
        BlockEntity tileEntity = worldIn.m_7702_(pos);
        if (tileEntity instanceof FrameTile && ((FrameTile)tileEntity).getControllerPos() != null && (controller = worldIn.m_7702_(((FrameTile)tileEntity).getControllerPos())) instanceof ControllerTile) {
            ((ControllerTile)controller).setShouldCheckForStructure(true);
        }
        super.m_6810_(state, worldIn, pos, newState, isMoving);
    }

    @Nonnull
    public RotatableBlock.RotationType getRotationType() {
        return RotatableBlock.RotationType.NONE;
    }

    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return BasicFrameTile::new;
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        super.m_7926_(builder);
    }
}

