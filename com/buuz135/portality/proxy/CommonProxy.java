/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.event.handler.EventManager
 *  net.minecraft.client.Minecraft
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$RightClickBlock
 *  net.minecraftforge.eventbus.api.Event$Result
 *  net.minecraftforge.registries.RegistryObject
 *  org.apache.commons.lang3.tuple.Pair
 */
package com.buuz135.portality.proxy;

import com.buuz135.portality.tile.ControllerTile;
import com.hrznstudio.titanium.event.handler.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.Pair;

public class CommonProxy {
    public static Pair<RegistryObject<Block>, RegistryObject<BlockEntityType<?>>> BLOCK_CONTROLLER;
    public static Pair<RegistryObject<Block>, RegistryObject<BlockEntityType<?>>> BLOCK_FRAME;
    public static Pair<RegistryObject<Block>, RegistryObject<BlockEntityType<?>>> BLOCK_GENERATOR;
    public static Pair<RegistryObject<Block>, RegistryObject<BlockEntityType<?>>> BLOCK_INTERDIMENSIONAL_MODULE;
    public static Pair<RegistryObject<Block>, RegistryObject<BlockEntityType<?>>> BLOCK_CAPABILITY_ITEM_MODULE;
    public static Pair<RegistryObject<Block>, RegistryObject<BlockEntityType<?>>> BLOCK_CAPABILITY_FLUID_MODULE;
    public static Pair<RegistryObject<Block>, RegistryObject<BlockEntityType<?>>> BLOCK_CAPABILITY_ENERGY_MODULE;
    public static RegistryObject<Item> TELEPORTATION_TOKEN_ITEM;

    public void onCommon() {
        EventManager.forge(PlayerInteractEvent.RightClickBlock.class).process(this::onInteract).subscribe();
    }

    @OnlyIn(value=Dist.CLIENT)
    public void onClient(Minecraft instance) {
    }

    public void onInteract(PlayerInteractEvent.RightClickBlock event) {
        ControllerTile controller;
        if (event.getPlayer().m_6047_() && event.getPlayer().f_19853_.m_8055_(event.getPos()).m_60734_().equals(BLOCK_CONTROLLER) && !(controller = (ControllerTile)event.getWorld().m_7702_(event.getPos())).getDisplay().m_41656_(event.getItemStack())) {
            event.setUseBlock(Event.Result.ALLOW);
        }
    }
}

