/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.TextComponent
 *  net.minecraft.network.chat.TranslatableComponent
 */
package com.buuz135.portality.gui;

import com.buuz135.portality.Portality;
import com.buuz135.portality.gui.ControllerScreen;
import com.buuz135.portality.network.PortalRenameMessage;
import com.buuz135.portality.tile.ControllerTile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class RenameControllerScreen
extends Screen {
    private final ControllerTile controller;
    private EditBox textFieldWidget;
    private Button confirm;

    public RenameControllerScreen(ControllerTile controller) {
        super((Component)new TranslatableComponent("portality.gui.controller.rename"));
        this.controller = controller;
    }

    protected void m_7856_() {
        super.m_7856_();
        int textFieldWidth = 140;
        this.textFieldWidget = new EditBox(Minecraft.m_91087_().f_91062_, this.f_96543_ / 2 - textFieldWidth / 2, this.f_96544_ / 2 - 10, textFieldWidth, 18, (Component)new TextComponent(""));
        this.textFieldWidget.m_94190_(false);
        this.textFieldWidget.m_94199_(28);
        this.textFieldWidget.m_94208_(0);
        this.textFieldWidget.m_94144_(this.controller.getPortalDisplayName());
        this.textFieldWidget.m_94178_(true);
        this.m_142416_((GuiEventListener)this.textFieldWidget);
        this.confirm = new Button(this.f_96543_ / 2 + textFieldWidth / 2 + 5, this.f_96544_ / 2 - 10, 50, 18, (Component)new TextComponent("Confirm"), button -> {
            Portality.NETWORK.get().sendToServer((Object)new PortalRenameMessage(this.textFieldWidget.m_94155_(), this.controller.m_58899_()));
            Minecraft.m_91087_().m_91152_((Screen)new ControllerScreen(this.controller));
        });
        this.m_142416_((GuiEventListener)this.confirm);
    }

    public void m_6305_(PoseStack matrixStack, int p_render_1_, int p_render_2_, float p_render_3_) {
        this.m_96558_(matrixStack, 0);
        super.m_6305_(matrixStack, p_render_1_, p_render_2_, p_render_3_);
        String rename = new TranslatableComponent("portality.gui.controller.rename").getString();
        Minecraft.m_91087_().f_91062_.m_92750_(matrixStack, rename, (float)(this.f_96543_ / 2 - Minecraft.m_91087_().f_91062_.m_92895_(rename) / 2), (float)(this.f_96544_ / 2 - 30), 0xFFFFFF);
    }

    public boolean m_7043_() {
        return false;
    }

    public void m_7861_() {
    }
}

