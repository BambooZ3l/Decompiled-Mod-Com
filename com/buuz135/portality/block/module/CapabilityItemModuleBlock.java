/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType$BlockEntitySupplier
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.items.CapabilityItemHandler
 *  net.minecraftforge.items.IItemHandler
 *  net.minecraftforge.items.ItemHandlerHelper
 */
package com.buuz135.portality.block.module;

import com.buuz135.portality.block.module.CapabilityModuleBlock;
import com.buuz135.portality.tile.ItemModuleTile;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class CapabilityItemModuleBlock
extends CapabilityModuleBlock<IItemHandler, ItemModuleTile> {
    public CapabilityItemModuleBlock() {
        super("module_items", ItemModuleTile.class);
    }

    @Override
    public Capability<IItemHandler> getCapability() {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    void internalWork(Level current, BlockPos myself, Level otherWorld, List<BlockPos> compatibleBlockPos) {
        current.m_7702_(myself).getCapability(this.getCapability(), Direction.UP).ifPresent(handlerSelf -> {
            for (BlockPos otherPos : compatibleBlockPos) {
                BlockEntity otherTile = otherWorld.m_7702_(otherPos);
                if (otherTile == null) continue;
                otherTile.getCapability(this.getCapability(), Direction.UP).ifPresent(handlerOther -> {
                    for (int i = 0; i < handlerSelf.getSlots(); ++i) {
                        ItemStack stack = handlerSelf.getStackInSlot(i);
                        if (stack.m_41619_() || !ItemHandlerHelper.insertItem((IItemHandler)handlerOther, (ItemStack)stack, (boolean)true).m_41619_()) continue;
                        ItemHandlerHelper.insertItem((IItemHandler)handlerOther, (ItemStack)stack.m_41777_(), (boolean)false);
                        handlerSelf.getStackInSlot(i).m_41764_(0);
                        return;
                    }
                });
            }
        });
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return ItemModuleTile::new;
    }
}

