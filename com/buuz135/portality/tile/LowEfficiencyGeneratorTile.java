/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.annotation.Save
 *  com.hrznstudio.titanium.block.BasicTileBlock
 *  com.hrznstudio.titanium.block.tile.GeneratorTile
 *  com.hrznstudio.titanium.client.screen.asset.IAssetProvider
 *  com.hrznstudio.titanium.component.IComponentHarness
 *  com.hrznstudio.titanium.component.inventory.InventoryComponent
 *  com.hrznstudio.titanium.component.inventory.SidedInventoryComponent
 *  com.hrznstudio.titanium.component.progress.ProgressBarComponent
 *  com.hrznstudio.titanium.component.progress.ProgressBarComponent$BarDirection
 *  javax.annotation.Nonnull
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.entity.FurnaceBlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.registries.RegistryObject
 */
package com.buuz135.portality.tile;

import com.buuz135.portality.gui.TileAssetProvider;
import com.buuz135.portality.proxy.CommonProxy;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.block.tile.GeneratorTile;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import com.hrznstudio.titanium.component.IComponentHarness;
import com.hrznstudio.titanium.component.inventory.InventoryComponent;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import com.hrznstudio.titanium.component.progress.ProgressBarComponent;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

public class LowEfficiencyGeneratorTile
extends GeneratorTile<LowEfficiencyGeneratorTile> {
    @Save
    private SidedInventoryComponent<LowEfficiencyGeneratorTile> fuel = (SidedInventoryComponent)new SidedInventoryComponent("fuel", 46, 22, 1, 0).setColor(DyeColor.ORANGE).setColorGuiEnabled(false).setInputFilter((itemStack, integer) -> FurnaceBlockEntity.m_58399_((ItemStack)itemStack)).setComponentHarness((IComponentHarness)this);

    public LowEfficiencyGeneratorTile(BlockPos pos, BlockState state) {
        super((BasicTileBlock)((RegistryObject)CommonProxy.BLOCK_GENERATOR.getLeft()).get(), (BlockEntityType)((RegistryObject)CommonProxy.BLOCK_GENERATOR.getRight()).get(), pos, state);
        this.addInventory((InventoryComponent)this.fuel);
    }

    public InteractionResult onActivated(Player playerIn, InteractionHand hand, Direction facing, double hitX, double hitY, double hitZ) {
        if (super.onActivated(playerIn, hand, facing, hitX, hitY, hitZ) != InteractionResult.SUCCESS) {
            this.openGui(playerIn);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public IAssetProvider getAssetProvider() {
        return TileAssetProvider.PROVIDER;
    }

    @Nonnull
    public LowEfficiencyGeneratorTile getSelf() {
        return this;
    }

    public int consumeFuel() {
        int time = FurnaceBlockEntity.m_58423_().getOrDefault(this.fuel.getStackInSlot(0).m_41720_(), 100);
        this.fuel.getStackInSlot(0).m_41774_(1);
        return time;
    }

    public boolean canStart() {
        return !this.fuel.getStackInSlot(0).m_41619_() && FurnaceBlockEntity.m_58423_().get(this.fuel.getStackInSlot(0).m_41720_()) != null;
    }

    public int getEnergyProducedEveryTick() {
        return 40;
    }

    public ProgressBarComponent<LowEfficiencyGeneratorTile> getProgressBar() {
        return new ProgressBarComponent(30, 20, 0, 100).setComponentHarness((IComponentHarness)this).setBarDirection(ProgressBarComponent.BarDirection.VERTICAL_UP).setColor(DyeColor.CYAN);
    }

    public int getEnergyCapacity() {
        return 100000;
    }

    public int getExtractingEnergy() {
        return 100000;
    }
}

