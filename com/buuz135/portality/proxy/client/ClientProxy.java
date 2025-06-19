/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.event.handler.EventManager
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.PlayerModel
 *  net.minecraft.client.player.AbstractClientPlayer
 *  net.minecraft.client.renderer.ItemBlockRenderTypes
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.LivingEntityRenderer
 *  net.minecraft.client.renderer.entity.layers.RenderLayer
 *  net.minecraft.client.renderer.entity.player.PlayerRenderer
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraftforge.client.event.EntityRenderersEvent$AddLayers
 *  net.minecraftforge.client.event.EntityRenderersEvent$RegisterRenderers
 *  net.minecraftforge.client.event.TextureStitchEvent$Pre
 *  net.minecraftforge.registries.RegistryObject
 */
package com.buuz135.portality.proxy.client;

import com.buuz135.portality.proxy.CommonProxy;
import com.buuz135.portality.proxy.client.IPortalColor;
import com.buuz135.portality.proxy.client.render.AuraRender;
import com.buuz135.portality.proxy.client.render.TESRPortal;
import com.hrznstudio.titanium.event.handler.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.registries.RegistryObject;

public class ClientProxy
extends CommonProxy {
    public ClientProxy() {
        EventManager.mod(EntityRenderersEvent.RegisterRenderers.class).process(registerRenderers -> registerRenderers.registerBlockEntityRenderer((BlockEntityType)((RegistryObject)CommonProxy.BLOCK_CONTROLLER.getRight()).get(), TESRPortal::new)).subscribe();
        EventManager.mod(EntityRenderersEvent.AddLayers.class).process(registerRenderers -> {
            for (String skin : registerRenderers.getSkins()) {
                PlayerRenderer renderer = (PlayerRenderer)registerRenderers.getSkin(skin);
                renderer.m_115326_((RenderLayer)new AuraRender((LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>)renderer));
            }
        }).subscribe();
        EventManager.mod(TextureStitchEvent.Pre.class).process(pre -> {
            if (pre.getAtlas().equals(InventoryMenu.f_39692_)) {
                pre.addSprite(TESRPortal.TEXTURE);
            }
        }).subscribe();
    }

    @Override
    public void onClient(Minecraft instance) {
        super.onClient(instance);
        ItemBlockRenderTypes.setRenderLayer((Block)((Block)((RegistryObject)CommonProxy.BLOCK_CONTROLLER.getLeft()).get()), (RenderType)RenderType.m_110463_());
        ItemBlockRenderTypes.setRenderLayer((Block)((Block)((RegistryObject)CommonProxy.BLOCK_FRAME.getLeft()).get()), (RenderType)RenderType.m_110463_());
        ItemBlockRenderTypes.setRenderLayer((Block)((Block)((RegistryObject)CommonProxy.BLOCK_CAPABILITY_ENERGY_MODULE.getLeft()).get()), (RenderType)RenderType.m_110463_());
        ItemBlockRenderTypes.setRenderLayer((Block)((Block)((RegistryObject)CommonProxy.BLOCK_CAPABILITY_FLUID_MODULE.getLeft()).get()), (RenderType)RenderType.m_110463_());
        ItemBlockRenderTypes.setRenderLayer((Block)((Block)((RegistryObject)CommonProxy.BLOCK_INTERDIMENSIONAL_MODULE.getLeft()).get()), (RenderType)RenderType.m_110463_());
        ItemBlockRenderTypes.setRenderLayer((Block)((Block)((RegistryObject)CommonProxy.BLOCK_CAPABILITY_ITEM_MODULE.getLeft()).get()), (RenderType)RenderType.m_110463_());
        Minecraft.m_91087_().m_91298_().m_92589_((state, world, pos, index) -> {
            BlockEntity tileEntity;
            if (index == 0 && world != null && (tileEntity = world.m_7702_(pos)) instanceof IPortalColor) {
                return ((IPortalColor)tileEntity).getColor();
            }
            return -16739073;
        }, new Block[]{(Block)((RegistryObject)CommonProxy.BLOCK_FRAME.getLeft()).get(), (Block)((RegistryObject)CommonProxy.BLOCK_CONTROLLER.getLeft()).get(), (Block)((RegistryObject)CommonProxy.BLOCK_CAPABILITY_ENERGY_MODULE.getLeft()).get(), (Block)((RegistryObject)CommonProxy.BLOCK_CAPABILITY_FLUID_MODULE.getLeft()).get(), (Block)((RegistryObject)CommonProxy.BLOCK_CAPABILITY_ITEM_MODULE.getLeft()).get(), (Block)((RegistryObject)CommonProxy.BLOCK_INTERDIMENSIONAL_MODULE.getLeft()).get()});
    }
}

