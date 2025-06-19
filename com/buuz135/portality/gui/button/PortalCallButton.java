/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.client.screen.ScreenAddonScreen
 *  com.hrznstudio.titanium.client.screen.addon.BasicScreenAddon
 *  com.hrznstudio.titanium.client.screen.asset.IAssetProvider
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.network.chat.TranslatableComponent
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.level.Level
 */
package com.buuz135.portality.gui.button;

import com.buuz135.portality.Portality;
import com.buuz135.portality.data.PortalLinkData;
import com.buuz135.portality.gui.PortalsScreen;
import com.buuz135.portality.network.PortalLinkMessage;
import com.buuz135.portality.tile.ControllerTile;
import com.hrznstudio.titanium.client.screen.ScreenAddonScreen;
import com.hrznstudio.titanium.client.screen.addon.BasicScreenAddon;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

public class PortalCallButton
extends BasicScreenAddon {
    private final CallAction action;
    private final ControllerTile controller;
    private final PortalsScreen guiPortals;
    private int guiX;
    private int guiY;

    public PortalCallButton(int x, int y, ControllerTile tile, CallAction action, PortalsScreen guiPortals) {
        super(x, y);
        this.action = action;
        this.controller = tile;
        this.guiPortals = guiPortals;
        this.guiX = 0;
        this.guiY = 0;
    }

    public void drawBackgroundLayer(PoseStack stack, Screen screen, IAssetProvider provider, int guiX, int guiY, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.m_157456_((int)0, (ResourceLocation)new ResourceLocation("portality", "textures/gui/portals.png"));
        screen.m_93228_(stack, this.getPosX(), this.getPosY(), 0, 187, this.getXSize(), this.getYSize());
        this.guiX = guiX;
        this.guiY = guiY;
    }

    public boolean m_5953_(double mouseX, double mouseY) {
        return super.m_5953_(mouseX + (double)this.guiX, mouseY + (double)this.guiY);
    }

    public int getXSize() {
        return 51;
    }

    public int getYSize() {
        return 22;
    }

    public void drawForegroundLayer(PoseStack stack, Screen screen, IAssetProvider provider, int guiX, int guiY, int mouseX, int mouseY, float partial) {
        Screen.m_93208_((PoseStack)stack, (Font)Minecraft.m_91087_().f_91062_, (String)new TranslatableComponent(this.action.getName()).getString(), (int)(this.getPosX() + 25), (int)(this.getPosY() + 7), (int)(this.m_5953_(mouseX - guiX, mouseY - guiY) ? 0xFFFFA0 : -1));
        RenderSystem.m_157429_((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public boolean m_6375_(double mouseX, double mouseY, int button) {
        Screen screen;
        if (this.guiPortals.getSelectedPortal() != null && (screen = Minecraft.m_91087_().f_91080_) instanceof ScreenAddonScreen) {
            if (!this.m_5953_(mouseX - (double)((ScreenAddonScreen)screen).x, mouseY - (double)((ScreenAddonScreen)screen).y)) {
                return false;
            }
            Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)new SimpleSoundInstance(SoundEvents.f_12490_, SoundSource.PLAYERS, 0.2f, 1.0f, Minecraft.m_91087_().f_91074_.m_142538_()));
            Portality.NETWORK.get().sendToServer((Object)new PortalLinkMessage(this.action.getId(), new PortalLinkData((ResourceKey<Level>)this.controller.m_58904_().m_46472_(), this.controller.m_58899_(), true, this.guiPortals.getSelectedPortal().getName(), this.guiPortals.getSelectedPortal().isToken()), new PortalLinkData(this.guiPortals.getSelectedPortal().getDimension(), this.guiPortals.getSelectedPortal().getLocation(), false, this.guiPortals.getSelectedPortal().getName(), this.guiPortals.getSelectedPortal().isToken())));
            Minecraft.m_91087_().m_91152_(null);
            return true;
        }
        return false;
    }

    public static enum CallAction {
        OPEN(0, "portality.display.dial"),
        ONCE(1, "portality.display.dial_once"),
        FORCE(2, "portality.display.force");

        private int id;
        private String name;

        private CallAction(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }
    }
}

