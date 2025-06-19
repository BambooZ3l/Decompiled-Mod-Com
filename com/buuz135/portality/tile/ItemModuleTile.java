/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.annotation.Save
 *  com.hrznstudio.titanium.block.BasicTileBlock
 *  com.hrznstudio.titanium.component.IComponentHarness
 *  com.hrznstudio.titanium.component.inventory.InventoryComponent
 *  com.hrznstudio.titanium.component.inventory.SidedInventoryComponent
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
import com.hrznstudio.titanium.component.inventory.InventoryComponent;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

public class ItemModuleTile
extends ModuleTile<ItemModuleTile> {
    @Save
    public SidedInventoryComponent<ItemModuleTile> handler = (SidedInventoryComponent)new SidedInventoryComponent("inventory", 52, 20, 12, 0).setColor(DyeColor.YELLOW).setColorGuiEnabled(false).setComponentHarness((IComponentHarness)this).setRange(4, 3);

    public ItemModuleTile(BlockPos pos, BlockState state) {
        super((BasicTileBlock)((RegistryObject)CommonProxy.BLOCK_CAPABILITY_ITEM_MODULE.getLeft()).get(), (BlockEntityType)((RegistryObject)CommonProxy.BLOCK_CAPABILITY_ITEM_MODULE.getRight()).get(), pos, state);
        this.addInventory((InventoryComponent)this.handler);
    }

    @Nonnull
    public ItemModuleTile getSelf() {
        return this;
    }
}

