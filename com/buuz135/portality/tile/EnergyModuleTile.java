/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.annotation.Save
 *  com.hrznstudio.titanium.block.BasicTileBlock
 *  com.hrznstudio.titanium.block.tile.ActiveTile
 *  com.hrznstudio.titanium.client.screen.addon.EnergyBarScreenAddon
 *  com.hrznstudio.titanium.component.IComponentHarness
 *  com.hrznstudio.titanium.component.energy.EnergyStorageComponent
 *  javax.annotation.Nonnull
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.util.LazyOptional
 *  net.minecraftforge.energy.CapabilityEnergy
 *  net.minecraftforge.energy.IEnergyStorage
 *  net.minecraftforge.registries.RegistryObject
 */
package com.buuz135.portality.tile;

import com.buuz135.portality.proxy.CommonProxy;
import com.buuz135.portality.tile.ModuleTile;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.block.tile.ActiveTile;
import com.hrznstudio.titanium.client.screen.addon.EnergyBarScreenAddon;
import com.hrznstudio.titanium.component.IComponentHarness;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.registries.RegistryObject;

public class EnergyModuleTile
extends ModuleTile<EnergyModuleTile> {
    @Save
    private final EnergyStorageComponent<EnergyModuleTile> energyStorage;
    private final LazyOptional<IEnergyStorage> lazyEnergyStorage = LazyOptional.of(this::getEnergyStorage);

    public EnergyModuleTile(BlockPos pos, BlockState state) {
        super((BasicTileBlock)((RegistryObject)CommonProxy.BLOCK_CAPABILITY_ENERGY_MODULE.getLeft()).get(), (BlockEntityType)((RegistryObject)CommonProxy.BLOCK_CAPABILITY_ENERGY_MODULE.getRight()).get(), pos, state);
        this.energyStorage = new EnergyStorageComponent(10000, 10000, 10000, 10, 20);
        this.energyStorage.setComponentHarness((IComponentHarness)this.getSelf());
    }

    @OnlyIn(value=Dist.CLIENT)
    public void initClient() {
        super.initClient();
        this.addGuiAddonFactory(() -> new EnergyBarScreenAddon(10, 20, this.energyStorage));
    }

    @Nonnull
    public EnergyStorageComponent<EnergyModuleTile> getEnergyStorage() {
        return this.energyStorage;
    }

    @Nonnull
    public LazyOptional getCapability(@Nonnull Capability cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY) {
            return this.lazyEnergyStorage.cast();
        }
        return super.getCapability(cap, side);
    }

    public void serverTick(Level level, BlockPos pos, BlockState state, EnergyModuleTile blockEntity) {
        super.serverTick(level, pos, state, (ActiveTile)blockEntity);
        if (!this.isInput()) {
            for (Direction facing : Direction.values()) {
                BlockPos checking = this.f_58858_.m_142300_(facing);
                BlockEntity checkingTile = this.f_58857_.m_7702_(checking);
                if (checkingTile == null) continue;
                checkingTile.getCapability(CapabilityEnergy.ENERGY, facing.m_122424_()).ifPresent(storage -> {
                    int energy = storage.receiveEnergy(Math.min(this.energyStorage.getEnergyStored(), 1000), false);
                    if (energy > 0) {
                        this.energyStorage.extractEnergy(energy, false);
                        return;
                    }
                });
            }
        }
    }

    @Nonnull
    public EnergyModuleTile getSelf() {
        return this;
    }
}

