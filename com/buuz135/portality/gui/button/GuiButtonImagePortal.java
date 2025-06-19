/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Lighting
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiComponent
 *  net.minecraft.client.gui.components.ImageButton
 *  net.minecraft.resources.ResourceLocation
 */
package com.buuz135.portality.gui.button;

import com.buuz135.portality.data.PortalInformation;
import com.buuz135.portality.gui.PortalsScreen;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.resources.ResourceLocation;

public class GuiButtonImagePortal
extends ImageButton {
    private PortalInformation information;
    private PortalsScreen portals;

    public GuiButtonImagePortal(PortalsScreen guiPortals, PortalInformation information, int x, int y, int xSize, int ySize, int textureX, int textureY, int offset, ResourceLocation location) {
        super(x, y, xSize, ySize, textureX, textureY, offset, location, p_onPress_1_ -> {});
        this.information = information;
        this.portals = guiPortals;
    }

    public void m_6303_(PoseStack stack, int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
        RenderSystem.m_157429_((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        super.m_6303_(stack, p_renderButton_1_, p_renderButton_2_, p_renderButton_3_);
        stack.m_85836_();
        Minecraft.m_91087_().m_91291_().m_115203_(this.information.getDisplay(), this.f_93620_ + 5, this.f_93621_ + 3);
        Font fontRenderer = Minecraft.m_91087_().f_91062_;
        ChatFormatting color = ChatFormatting.RESET;
        if (this.information.isPrivate()) {
            color = ChatFormatting.GOLD;
        }
        if (this.information.isActive()) {
            color = ChatFormatting.RED;
        }
        fontRenderer.m_92750_(stack, color + this.information.getName().substring(0, Math.min(this.information.getName().length(), 25)), (float)(this.f_93620_ + 28), (float)(7 + this.f_93621_), this.m_5953_(p_renderButton_1_, p_renderButton_2_) ? 0xFFFFA0 : -1);
        RenderSystem.m_157429_((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        if (this.information.isPrivate()) {
            RenderSystem.m_69465_();
            RenderSystem.m_157456_((int)0, (ResourceLocation)new ResourceLocation("portality", "textures/gui/lock.png"));
            GuiComponent.m_93133_((PoseStack)stack, (int)(this.f_93620_ + 4), (int)(this.f_93621_ + 14), (float)0.0f, (float)0.0f, (int)8, (int)8, (int)8, (int)8);
            RenderSystem.m_69482_();
        }
        Lighting.m_84931_();
        if (this.portals.getSelectedPortal() == this.information) {
            RenderSystem.m_157456_((int)0, (ResourceLocation)new ResourceLocation("portality", "textures/gui/portals.png"));
            Minecraft.m_91087_().f_91080_.m_93228_(stack, this.f_93620_, this.f_93621_, 0, 210, 157, 22);
        }
        stack.m_85849_();
    }

    public PortalInformation getInformation() {
        return this.information;
    }
}

