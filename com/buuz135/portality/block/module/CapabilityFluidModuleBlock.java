/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType$BlockEntitySupplier
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.fluids.capability.CapabilityFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package com.buuz135.portality.block.module;

import com.buuz135.portality.block.module.CapabilityModuleBlock;
import com.buuz135.portality.tile.FluidModuleTile;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class CapabilityFluidModuleBlock
extends CapabilityModuleBlock<IFluidHandler, FluidModuleTile> {
    public CapabilityFluidModuleBlock() {
        super("module_fluids", FluidModuleTile.class);
    }

    @Override
    public Capability<IFluidHandler> getCapability() {
        return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
    }

    @Override
    void internalWork(Level current, BlockPos myself, Level otherWorld, List<BlockPos> compatibleBlockPos) {
        current.m_7702_(myself).getCapability(this.getCapability(), null).ifPresent(handler -> {
            if (!handler.drain(500, IFluidHandler.FluidAction.SIMULATE).isEmpty()) {
                for (BlockPos pos : compatibleBlockPos) {
                    BlockEntity otherTile = otherWorld.m_7702_(pos);
                    if (otherTile == null) continue;
                    otherTile.getCapability(this.getCapability(), null).ifPresent(otherHandler -> {
                        int filled = otherHandler.fill(handler.drain(500, IFluidHandler.FluidAction.SIMULATE), IFluidHandler.FluidAction.EXECUTE);
                        handler.drain(filled, IFluidHandler.FluidAction.EXECUTE);
                        if (filled > 0) {
                            return;
                        }
                    });
                }
            }
        });
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return FluidModuleTile::new;
    }
}

