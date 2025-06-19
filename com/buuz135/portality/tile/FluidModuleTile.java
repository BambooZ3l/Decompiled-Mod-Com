/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.annotation.Save
 *  com.hrznstudio.titanium.block.BasicTileBlock
 *  com.hrznstudio.titanium.component.IComponentHarness
 *  com.hrznstudio.titanium.component.fluid.FluidTankComponent
 *  com.hrznstudio.titanium.component.fluid.SidedFluidTankComponent
 *  javax.annotation.Nonnull
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.registries.RegistryObject
 */
package com.buuz135.portality.tile;

import com.buuz135.portality.proxy.CommonProxy;
import com.buuz135.portality.tile.ModuleTile;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.component.IComponentHarness;
import com.hrznstudio.titanium.component.fluid.FluidTankComponent;
import com.hrznstudio.titanium.component.fluid.SidedFluidTankComponent;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

public class FluidModuleTile
extends ModuleTile<FluidModuleTile> {
    @Save
    private SidedFluidTankComponent<FluidModuleTile> tank = (SidedFluidTankComponent)new SidedFluidTankComponent("tank", 16000, 76, 20, 0).setColor(DyeColor.CYAN).setComponentHarness((IComponentHarness)this);

    public FluidModuleTile(BlockPos pos, BlockState state) {
        super((BasicTileBlock)((RegistryObject)CommonProxy.BLOCK_CAPABILITY_FLUID_MODULE.getLeft()).get(), (BlockEntityType)((RegistryObject)CommonProxy.BLOCK_CAPABILITY_FLUID_MODULE.getRight()).get(), pos, state);
        this.addTank((FluidTankComponent)this.tank);
    }

    @Nonnull
    public FluidModuleTile getSelf() {
        return this;
    }
}

