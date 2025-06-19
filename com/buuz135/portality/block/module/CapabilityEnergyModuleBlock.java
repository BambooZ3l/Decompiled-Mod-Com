/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType$BlockEntitySupplier
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.energy.CapabilityEnergy
 *  net.minecraftforge.energy.IEnergyStorage
 */
package com.buuz135.portality.block.module;

import com.buuz135.portality.block.module.CapabilityModuleBlock;
import com.buuz135.portality.tile.EnergyModuleTile;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class CapabilityEnergyModuleBlock
extends CapabilityModuleBlock<IEnergyStorage, EnergyModuleTile> {
    public CapabilityEnergyModuleBlock() {
        super("module_energy", EnergyModuleTile.class);
    }

    @Override
    public Capability<IEnergyStorage> getCapability() {
        return CapabilityEnergy.ENERGY;
    }

    @Override
    void internalWork(Level current, BlockPos myself, Level otherWorld, List<BlockPos> compatibleBlockPos) {
        current.m_7702_(myself).getCapability(this.getCapability()).ifPresent(storage -> {
            for (BlockPos pos : compatibleBlockPos) {
                BlockEntity entity = otherWorld.m_7702_(pos);
                if (entity == null) continue;
                entity.getCapability(this.getCapability()).ifPresent(otherStorage -> {
                    int energy = otherStorage.receiveEnergy(Math.min(storage.getEnergyStored(), 5000), false);
                    storage.extractEnergy(energy, false);
                    if (energy > 0) {
                        return;
                    }
                });
            }
        });
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return EnergyModuleTile::new;
    }
}

