/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.block.RotatableBlock
 *  com.hrznstudio.titanium.block.RotatableBlock$RotationType
 *  javax.annotation.Nonnull
 *  javax.annotation.Nullable
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntityType$BlockEntitySupplier
 *  net.minecraft.world.level.block.state.BlockBehaviour
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 */
package com.buuz135.portality.block;

import com.buuz135.portality.Portality;
import com.buuz135.portality.tile.LowEfficiencyGeneratorTile;
import com.hrznstudio.titanium.block.RotatableBlock;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class GeneratorBlock
extends RotatableBlock<LowEfficiencyGeneratorTile> {
    public GeneratorBlock() {
        super("generator", BlockBehaviour.Properties.m_60926_((BlockBehaviour)Blocks.f_50075_), LowEfficiencyGeneratorTile.class);
        this.setItemGroup(Portality.TAB);
    }

    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return LowEfficiencyGeneratorTile::new;
    }

    @Nonnull
    public RotatableBlock.RotationType getRotationType() {
        return RotatableBlock.RotationType.FOUR_WAY;
    }

    @Nullable
    public BlockState m_5573_(BlockPlaceContext context) {
        return (BlockState)this.m_49966_().m_61124_((Property)FACING_HORIZONTAL, (Comparable)context.m_8125_().m_122424_());
    }
}

