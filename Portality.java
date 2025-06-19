/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.event.handler.EventManager
 *  com.hrznstudio.titanium.module.ModuleController
 *  com.hrznstudio.titanium.network.NetworkHandler
 *  com.hrznstudio.titanium.reward.Reward
 *  com.hrznstudio.titanium.reward.RewardGiver
 *  com.hrznstudio.titanium.reward.RewardManager
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.data.DataProvider
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.TranslatableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$RightClickBlock
 *  net.minecraftforge.fml.DistExecutor
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
 *  net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.minecraftforge.forge.event.lifecycle.GatherDataEvent
 *  net.minecraftforge.registries.RegistryObject
 */
package com.buuz135.portality;

import com.buuz135.portality.block.ControllerBlock;
import com.buuz135.portality.block.FrameBlock;
import com.buuz135.portality.block.GeneratorBlock;
import com.buuz135.portality.block.InterdimensionalModuleBlock;
import com.buuz135.portality.block.module.CapabilityEnergyModuleBlock;
import com.buuz135.portality.block.module.CapabilityFluidModuleBlock;
import com.buuz135.portality.block.module.CapabilityItemModuleBlock;
import com.buuz135.portality.datagen.PortalityBlockTagsProvider;
import com.buuz135.portality.item.TeleportationTokenItem;
import com.buuz135.portality.network.PortalChangeColorMessage;
import com.buuz135.portality.network.PortalCloseMessage;
import com.buuz135.portality.network.PortalDisplayToggleMessage;
import com.buuz135.portality.network.PortalLinkMessage;
import com.buuz135.portality.network.PortalNetworkMessage;
import com.buuz135.portality.network.PortalPrivacyToggleMessage;
import com.buuz135.portality.network.PortalRenameMessage;
import com.buuz135.portality.network.PortalTeleportMessage;
import com.buuz135.portality.proxy.CommonProxy;
import com.buuz135.portality.proxy.PortalitySoundHandler;
import com.buuz135.portality.proxy.client.ClientProxy;
import com.buuz135.portality.tile.BasicFrameTile;
import com.buuz135.portality.tile.ControllerTile;
import com.hrznstudio.titanium.event.handler.EventManager;
import com.hrznstudio.titanium.module.ModuleController;
import com.hrznstudio.titanium.network.NetworkHandler;
import com.hrznstudio.titanium.reward.Reward;
import com.hrznstudio.titanium.reward.RewardGiver;
import com.hrznstudio.titanium.reward.RewardManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.data.DataProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

@Mod(value="portality")
public class Portality
extends ModuleController {
    public static final String MOD_ID = "portality";
    public static NetworkHandler NETWORK = new NetworkHandler("portality");
    public static final CreativeModeTab TAB = new CreativeModeTab("portality"){

        public ItemStack m_6976_() {
            return new ItemStack((ItemLike)((RegistryObject)CommonProxy.BLOCK_CONTROLLER.getLeft()).get());
        }
    };
    public static CommonProxy proxy;

    public Portality() {
        NETWORK.registerMessage(PortalPrivacyToggleMessage.class);
        NETWORK.registerMessage(PortalPrivacyToggleMessage.class);
        NETWORK.registerMessage(PortalRenameMessage.class);
        NETWORK.registerMessage(PortalNetworkMessage.Response.class);
        NETWORK.registerMessage(PortalLinkMessage.class);
        NETWORK.registerMessage(PortalCloseMessage.class);
        NETWORK.registerMessage(PortalTeleportMessage.class);
        NETWORK.registerMessage(PortalDisplayToggleMessage.class);
        NETWORK.registerMessage(PortalChangeColorMessage.class);
        proxy = (CommonProxy)DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
        EventManager.mod(FMLCommonSetupEvent.class).process(this::onCommon).subscribe();
        EventManager.mod(FMLClientSetupEvent.class).process(this::onClient).subscribe();
        RewardGiver giver = RewardManager.get().getGiver(UUID.fromString("d28b7061-fb92-4064-90fb-7e02b95a72a6"), "Buuz135");
        try {
            giver.addReward(new Reward(new ResourceLocation(MOD_ID, "aura"), new URL("https://raw.githubusercontent.com/Buuz135/Industrial-Foregoing/master/contributors.json"), () -> dist -> {}, Arrays.stream(AuraType.values()).map(Enum::toString).collect(Collectors.toList()).toArray(new String[0])));
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        EventManager.forge(PlayerInteractEvent.RightClickBlock.class).filter(event -> !event.getWorld().f_46443_ && event.getPlayer().m_6047_() && event.getWorld().m_7702_(event.getPos()) instanceof ControllerTile && !event.getPlayer().m_21120_(event.getHand()).m_41619_()).process(event -> {
            ControllerTile controllerTile = (ControllerTile)event.getWorld().m_7702_(event.getPos());
            ItemStack stack = event.getPlayer().m_21120_(event.getHand());
            if (!stack.m_41656_(controllerTile.getDisplay())) {
                if (stack.m_41720_() instanceof TeleportationTokenItem) {
                    if (stack.m_41782_()) {
                        controllerTile.addTeleportationToken(stack);
                        event.getPlayer().m_5661_((Component)new TranslatableComponent("portility.controller.info.added_token").m_130940_(ChatFormatting.GREEN), true);
                    }
                    return;
                }
                event.getPlayer().m_5661_((Component)new TranslatableComponent("portility.controller.info.icon_changed").m_130940_(ChatFormatting.GREEN), true);
                controllerTile.setDisplayNameEnabled(stack);
            }
        }).subscribe();
    }

    protected void initModules() {
        CommonProxy.BLOCK_CONTROLLER = this.getRegistries().registerBlockWithTile("controller", ControllerBlock::new);
        CommonProxy.BLOCK_FRAME = this.getRegistries().registerBlockWithTile("frame", () -> new FrameBlock<BasicFrameTile>("frame", BasicFrameTile.class));
        CommonProxy.BLOCK_CAPABILITY_ENERGY_MODULE = this.getRegistries().registerBlockWithTile("module_energy", CapabilityEnergyModuleBlock::new);
        CommonProxy.BLOCK_CAPABILITY_FLUID_MODULE = this.getRegistries().registerBlockWithTile("module_fluids", CapabilityFluidModuleBlock::new);
        CommonProxy.BLOCK_CAPABILITY_ITEM_MODULE = this.getRegistries().registerBlockWithTile("module_items", CapabilityItemModuleBlock::new);
        CommonProxy.BLOCK_INTERDIMENSIONAL_MODULE = this.getRegistries().registerBlockWithTile("module_interdimensional", InterdimensionalModuleBlock::new);
        CommonProxy.BLOCK_GENERATOR = this.getRegistries().registerBlockWithTile("generator", GeneratorBlock::new);
        CommonProxy.TELEPORTATION_TOKEN_ITEM = this.getRegistries().registerGeneric(Item.class, "teleportation_token", TeleportationTokenItem::new);
        PortalitySoundHandler.PORTAL = this.getRegistries().registerGeneric(SoundEvent.class, "portal", () -> new SoundEvent(new ResourceLocation(MOD_ID, "portal")));
        PortalitySoundHandler.PORTAL_TP = this.getRegistries().registerGeneric(SoundEvent.class, "portal_teleport", () -> new SoundEvent(new ResourceLocation(MOD_ID, "portal_teleport")));
    }

    public void onCommon(FMLCommonSetupEvent event) {
        proxy.onCommon();
    }

    public void onClient(FMLClientSetupEvent event) {
        proxy.onClient(Minecraft.m_91087_());
    }

    public void addDataProvider(GatherDataEvent event) {
        super.addDataProvider(event);
        event.getGenerator().m_123914_((DataProvider)new PortalityBlockTagsProvider(event.getGenerator(), MOD_ID, event.getExistingFileHelper()));
    }

    public static enum AuraType {
        PORTAL(new ResourceLocation("portality", "textures/blocks/player_render.png"), true),
        FORCE_FIELD(new ResourceLocation("textures/misc/forcefield.png"), true),
        UNDERWATER(new ResourceLocation("textures/misc/underwater.png"), true),
        SPOOK(new ResourceLocation("textures/misc/pumpkinblur.png"), false),
        END(new ResourceLocation("textures/environment/end_sky.png"), true),
        CLOUDS(new ResourceLocation("textures/environment/clouds.png"), true),
        RAIN(new ResourceLocation("textures/environment/rain.png"), true),
        SGA(new ResourceLocation("textures/font/ascii_sga.png"), true),
        ENCHANTED(new ResourceLocation("textures/misc/enchanted_item_glint.png"), true),
        BARS(new ResourceLocation("textures/gui/bars.png"), true),
        RECIPE_BOOK(new ResourceLocation("textures/gui/recipe_book.png"), true),
        END_PORTAL(new ResourceLocation("textures/entity/end_portal.png"), true),
        MOON(new ResourceLocation("textures/environment/moon_phases.png"), true);

        private final ResourceLocation resourceLocation;
        private final boolean enableBlend;

        private AuraType(ResourceLocation resourceLocation, boolean enableBlend) {
            this.resourceLocation = resourceLocation;
            this.enableBlend = enableBlend;
        }

        public ResourceLocation getResourceLocation() {
            return this.resourceLocation;
        }

        public boolean isEnableBlend() {
            return this.enableBlend;
        }
    }
}

