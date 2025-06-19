/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.api.IFactory
 *  com.hrznstudio.titanium.api.client.IScreenAddon
 *  com.hrznstudio.titanium.client.screen.ScreenAddonScreen
 *  com.hrznstudio.titanium.client.screen.addon.color.ColorPickerAddon
 *  com.hrznstudio.titanium.client.screen.asset.IAssetProvider
 *  com.hrznstudio.titanium.util.AssetUtil
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiComponent
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.TextComponent
 *  net.minecraft.resources.ResourceLocation
 */
package com.buuz135.portality.gui;

import com.buuz135.portality.gui.PortalityAssetProvider;
import com.buuz135.portality.gui.button.PortalSaveButton;
import com.buuz135.portality.tile.ControllerTile;
import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.client.IScreenAddon;
import com.hrznstudio.titanium.client.screen.ScreenAddonScreen;
import com.hrznstudio.titanium.client.screen.addon.color.ColorPickerAddon;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import com.hrznstudio.titanium.util.AssetUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

public class ChangeColorScreen
extends ScreenAddonScreen {
    private ControllerTile controllerTile;
    private EditBox textField;
    private int color;
    private ColorPickerAddon colorPickerAddon;
    private boolean textChangedManually;

    public ChangeColorScreen(ControllerTile tile) {
        super((IAssetProvider)PortalityAssetProvider.PROVIDER, false);
        this.controllerTile = tile;
        this.color = tile.getColor();
        this.textChangedManually = false;
    }

    public void m_7856_() {
        super.m_7856_();
        this.textField = new EditBox(Minecraft.m_91087_().f_91062_, this.x + 14, this.y + 120, 80, 16, (Component)new TextComponent(""));
        this.textField.m_94194_(true);
        this.textField.m_94182_(true);
        this.textField.m_94199_(6);
        this.textField.m_94151_(s -> {
            if (this.textChangedManually) {
                this.textChangedManually = false;
                return;
            }
            if (s.length() > 0) {
                try {
                    int tempColor = 0xFF000000 | Integer.parseInt(s, 16);
                    if (tempColor != this.color) {
                        this.color = tempColor;
                        this.colorPickerAddon.setColor(this.color);
                    }
                }
                catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
            }
        });
        this.updateColor(this.color);
        this.m_7787_((GuiEventListener)this.textField);
        this.getAddons().add(new PortalSaveButton(this.x + 110, this.y + 116, this.controllerTile, "Save", this));
    }

    public void renderBackground(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.m_7333_(stack);
        RenderSystem.m_157456_((int)0, (ResourceLocation)new ResourceLocation("portality", "textures/gui/color_change.png"));
        Minecraft.m_91087_().f_91080_.m_93228_(stack, this.x, this.y, 0, 0, 175, 146);
        GuiComponent.m_93172_((PoseStack)stack, (int)(this.x + 13), (int)(this.y + 9), (int)(this.x + 15 + 100), (int)(this.y + 91), (int)-16739073);
        GuiComponent.m_93172_((PoseStack)stack, (int)(this.x + 123), (int)(this.y + 9), (int)(this.x + 121 + 40), (int)(this.y + 91), (int)-16739073);
        GuiComponent.m_93172_((PoseStack)stack, (int)(this.x + 13), (int)(this.y + 99), (int)(this.x + 13 + 148), (int)(this.y + 109), (int)-16739073);
        super.renderBackground(stack, mouseX, mouseY, partialTicks);
        this.textField.m_6303_(stack, mouseX, mouseY, partialTicks);
        AssetUtil.drawHorizontalLine((PoseStack)stack, (int)(this.textField.f_93620_ - 1), (int)(this.textField.f_93620_ + this.textField.m_5711_()), (int)(this.textField.f_93621_ - 1), (int)-16739073);
        AssetUtil.drawHorizontalLine((PoseStack)stack, (int)(this.textField.f_93620_ - 1), (int)(this.textField.f_93620_ + this.textField.m_5711_()), (int)(this.textField.f_93621_ + this.textField.m_93694_()), (int)-16739073);
        AssetUtil.drawVerticalLine((PoseStack)stack, (int)(this.textField.f_93620_ - 1), (int)(this.textField.f_93621_ - 1), (int)(this.textField.f_93621_ + this.textField.m_93694_()), (int)-16739073);
        AssetUtil.drawVerticalLine((PoseStack)stack, (int)(this.textField.f_93620_ + this.textField.m_5711_()), (int)(this.textField.f_93621_ - 1), (int)(this.textField.f_93621_ + this.textField.m_93694_()), (int)-16739073);
    }

    public void m_96624_() {
        super.m_96624_();
        this.textField.m_94120_();
    }

    public List<IFactory<IScreenAddon>> guiAddons() {
        ArrayList<IFactory<IScreenAddon>> addons = new ArrayList<IFactory<IScreenAddon>>();
        addons.add(() -> {
            this.colorPickerAddon = new ColorPickerAddon(14, 10, this.color, this::updateColor);
            return this.colorPickerAddon;
        });
        return addons;
    }

    public void updateColor(int color) {
        this.color = color;
        if (this.textField != null) {
            this.textChangedManually = true;
            this.textField.m_94144_(Integer.toHexString(color).substring(2));
        }
    }

    public int getColor() {
        return this.color;
    }

    public boolean m_7933_(int keyCode, int scanCode, int modifiers) {
        return this.textField.m_7933_(keyCode, scanCode, modifiers) || super.m_7933_(keyCode, scanCode, modifiers);
    }

    public boolean m_7043_() {
        return false;
    }
}

