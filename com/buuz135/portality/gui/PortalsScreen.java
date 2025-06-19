/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.api.IFactory
 *  com.hrznstudio.titanium.api.client.IScreenAddon
 *  com.hrznstudio.titanium.client.screen.ScreenAddonScreen
 *  com.hrznstudio.titanium.client.screen.asset.IAssetProvider
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.TextComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  org.lwjgl.glfw.GLFW
 */
package com.buuz135.portality.gui;

import com.buuz135.portality.data.PortalInformation;
import com.buuz135.portality.gui.PortalityAssetProvider;
import com.buuz135.portality.gui.button.GuiButtonImagePortal;
import com.buuz135.portality.gui.button.PortalCallButton;
import com.buuz135.portality.tile.ControllerTile;
import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.client.IScreenAddon;
import com.hrznstudio.titanium.client.screen.ScreenAddonScreen;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.lwjgl.glfw.GLFW;

public class PortalsScreen
extends ScreenAddonScreen {
    private final int guiHeight;
    private final int guiWidth;
    private List<PortalInformation> informationList;
    private EditBox textField;
    private double scrolling = 0.0;
    private double lastScrolling = 0.0;
    private boolean isDragging;
    private int visiblePortalInformations;
    private List<GuiButtonImagePortal> portalButtons = new ArrayList<GuiButtonImagePortal>();
    private List<PortalInformation> currentlyShowing = new ArrayList<PortalInformation>();
    private PortalInformation selectedPortal;
    private ControllerTile controller;

    public PortalsScreen(ControllerTile controller) {
        super((IAssetProvider)PortalityAssetProvider.PROVIDER, false);
        this.guiWidth = 200;
        this.guiHeight = 186;
        this.controller = controller;
    }

    public void m_7856_() {
        super.m_7856_();
        this.x = this.f_96543_ / 2 - this.guiWidth / 2;
        this.y = this.f_96544_ / 2 - this.guiWidth / 2;
        if (this.informationList != null && !this.informationList.isEmpty()) {
            this.addPortalButtons();
        }
        this.textField = new EditBox(Minecraft.m_91087_().f_91062_, this.x + this.guiWidth - 131, this.y + 3, 100, 10, (Component)new TextComponent(""));
        this.textField.m_94178_(true);
        this.textField.m_94194_(true);
        this.textField.m_94182_(true);
        this.getAddons().add(new PortalCallButton(this.x + 9, this.y + this.guiHeight + 2, this.controller, PortalCallButton.CallAction.OPEN, this));
        this.getAddons().add(new PortalCallButton(this.x + 53 + 9, this.y + this.guiHeight + 2, this.controller, PortalCallButton.CallAction.ONCE, this));
        this.getAddons().add(new PortalCallButton(this.x + 106 + 9, this.y + this.guiHeight + 2, this.controller, PortalCallButton.CallAction.FORCE, this));
    }

    private void addPortalButtons() {
        if (this.informationList == null) {
            return;
        }
        ArrayList<PortalInformation> tempInformations = new ArrayList<PortalInformation>(this.informationList);
        tempInformations.removeIf(information -> information.isPrivate() && !information.getOwner().equals(Minecraft.m_91087_().f_91074_.m_142081_()));
        tempInformations.sort((o1, o2) -> Boolean.compare(o2.isPrivate(), o1.isPrivate()));
        if (!this.textField.m_94155_().isEmpty()) {
            tempInformations.removeIf(portalInformation -> !portalInformation.getName().toLowerCase().contains(this.textField.m_94155_().toLowerCase()));
        }
        this.portalButtons.forEach(x$0 -> this.m_169411_((GuiEventListener)x$0));
        this.portalButtons.clear();
        this.visiblePortalInformations = tempInformations.size();
        int pointer = (int)((double)(tempInformations.size() - 7) * this.scrolling);
        this.currentlyShowing = tempInformations;
        for (int i = pointer; i < pointer + 7; ++i) {
            if (tempInformations.size() <= i || i < 0) continue;
            final int finalI = i;
            GuiButtonImagePortal buttonImage = new GuiButtonImagePortal(this, (PortalInformation)tempInformations.get(finalI), this.x + 9, this.y + 19 + 23 * (finalI - pointer), 157, 22, 0, 234, 0, new ResourceLocation("portality", "textures/gui/portals.png")){

                public void m_5691_() {
                    PortalsScreen.this.selectedPortal = PortalsScreen.this.currentlyShowing.get(finalI + (int)((double)(PortalsScreen.this.currentlyShowing.size() - 7) * PortalsScreen.this.scrolling));
                }
            };
            this.m_142416_((GuiEventListener)buttonImage);
            this.portalButtons.add(buttonImage);
        }
    }

    public boolean m_7933_(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        boolean pressed = super.m_7933_(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
        Minecraft.m_91087_().m_6937_(() -> {
            this.scrolling = 0.0;
            this.lastScrolling = 0.0;
            this.addPortalButtons();
        });
        return pressed;
    }

    public void refresh(List<PortalInformation> informationList) {
        this.informationList = informationList;
        this.addPortalButtons();
    }

    public void renderBackground(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.checkForScrolling(mouseX, mouseY);
        this.m_7333_(stack);
        RenderSystem.m_157456_((int)0, (ResourceLocation)new ResourceLocation("portality", "textures/gui/portals.png"));
        Minecraft.m_91087_().f_91080_.m_93228_(stack, this.x, this.y, 0, 0, this.guiWidth, this.guiHeight);
        Minecraft.m_91087_().f_91080_.m_93228_(stack, this.x + this.guiWidth - 22, (int)((double)(this.y + 10) + 140.0 * this.scrolling), 200, 9, 18, 23);
        super.renderBackground(stack, mouseX, mouseY, partialTicks);
        this.textField.m_6303_(stack, mouseX, mouseY, partialTicks);
    }

    public List<IFactory<IScreenAddon>> guiAddons() {
        return Collections.emptyList();
    }

    public PortalInformation getSelectedPortal() {
        return this.selectedPortal;
    }

    public boolean m_7043_() {
        return false;
    }

    private void checkForScrolling(int mouseX, int mouseY) {
        if (GLFW.glfwGetMouseButton((long)Minecraft.m_91087_().m_91268_().m_85439_(), (int)0) == 1) {
            if (!this.isDragging && mouseX > this.x + this.guiWidth - 22 && mouseX < this.x + this.guiWidth - 22 + 18 && mouseY > this.y + 10 && mouseY < this.y + 10 + 151) {
                this.isDragging = true;
            }
            if (this.isDragging) {
                this.lastScrolling = this.scrolling;
                this.scrolling = Mth.m_14008_((double)((double)(mouseY -= 25 + this.y) / 128.0), (double)0.0, (double)1.0);
                this.addPortalButtons();
            }
        } else {
            this.isDragging = false;
        }
    }

    public boolean m_6050_(double x, double y, double z) {
        this.scrolling = Mth.m_14008_((double)(this.scrolling -= z / ((double)this.currentlyShowing.size() - 7.0)), (double)0.0, (double)1.0);
        this.addPortalButtons();
        return true;
    }
}

