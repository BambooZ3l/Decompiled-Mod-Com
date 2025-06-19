/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.api.IFactory
 *  com.hrznstudio.titanium.api.client.AssetTypes
 *  com.hrznstudio.titanium.api.client.IAssetType
 *  com.hrznstudio.titanium.api.client.IScreenAddon
 *  com.hrznstudio.titanium.api.client.assets.types.IBackgroundAsset
 *  com.hrznstudio.titanium.client.screen.ITileContainer
 *  com.hrznstudio.titanium.client.screen.ScreenAddonScreen
 *  com.hrznstudio.titanium.client.screen.addon.BasicScreenAddon
 *  com.hrznstudio.titanium.client.screen.asset.IAssetProvider
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.network.chat.TranslatableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.block.Block
 *  net.minecraftforge.registries.RegistryObject
 */
package com.buuz135.portality.gui;

import com.buuz135.portality.gui.PortalityAssetProvider;
import com.buuz135.portality.proxy.CommonProxy;
import com.buuz135.portality.tile.ControllerTile;
import com.buuz135.portality.util.BlockPosUtils;
import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.client.AssetTypes;
import com.hrznstudio.titanium.api.client.IAssetType;
import com.hrznstudio.titanium.api.client.IScreenAddon;
import com.hrznstudio.titanium.api.client.assets.types.IBackgroundAsset;
import com.hrznstudio.titanium.client.screen.ITileContainer;
import com.hrznstudio.titanium.client.screen.ScreenAddonScreen;
import com.hrznstudio.titanium.client.screen.addon.BasicScreenAddon;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class ControllerScreen
extends ScreenAddonScreen
implements ITileContainer<ControllerTile> {
    private ControllerTile controller;

    public ControllerScreen(ControllerTile controller) {
        super((IAssetProvider)PortalityAssetProvider.PROVIDER, true);
        this.controller = controller;
    }

    public void renderBackground(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        super.renderBackground(stack, mouseX, mouseY, partialTicks);
    }

    public void renderForeground(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        IBackgroundAsset background = (IBackgroundAsset)IAssetProvider.getAsset((IAssetProvider)PortalityAssetProvider.PROVIDER, (IAssetType)AssetTypes.BACKGROUND);
        String name = new TranslatableComponent(((Block)((RegistryObject)CommonProxy.BLOCK_CONTROLLER.getLeft()).get()).m_7705_()).getString();
        Font fontRenderer = Minecraft.m_91087_().f_91062_;
        fontRenderer.m_92750_(stack, ChatFormatting.DARK_AQUA + name, (float)(this.x + background.getArea().width / 2 - fontRenderer.m_92895_(name) / 2), (float)(this.y + 3), 0);
        fontRenderer.m_92750_(stack, I18n.m_118938_((String)"portality.gui.controller", (Object[])new Object[0]) + " " + this.controller.getPortalDisplayName().substring(0, Math.min(this.controller.getPortalDisplayName().length(), 26)), (float)(this.x + 10), (float)(this.y + 21), 0xFFFFFF);
        String string = I18n.m_118938_((String)"portality.gui.controller.private", (Object[])new Object[0]) + " " + this.controller.isPrivate();
        float f = this.x + 10;
        Objects.requireNonNull(fontRenderer);
        fontRenderer.m_92750_(stack, string, f, (float)(this.y + 21 + (9 + 1) * 1), 0xFFFFFF);
        String string2 = I18n.m_118938_((String)"portality.gui.controller.max_distance", (Object[])new Object[0]) + " " + BlockPosUtils.getMaxDistance(this.controller.getLength());
        float f2 = this.x + 10;
        Objects.requireNonNull(fontRenderer);
        fontRenderer.m_92750_(stack, string2, f2, (float)(this.y + 21 + (9 + 1) * 2), 0xFFFFFF);
        String string3 = I18n.m_118938_((String)"portality.gui.controller.interdimensional", (Object[])new Object[0]) + " " + this.controller.isInterdimensional();
        float f3 = this.x + 10;
        Objects.requireNonNull(fontRenderer);
        fontRenderer.m_92750_(stack, string3, f3, (float)(this.y + 21 + (9 + 1) * 3), 0xFFFFFF);
        String string4 = I18n.m_118938_((String)"portality.gui.controller.power", (Object[])new Object[0]) + " " + new DecimalFormat().format(this.controller.getEnergyStorage().getEnergyStored()) + " FE";
        float f4 = this.x + 10;
        Objects.requireNonNull(fontRenderer);
        fontRenderer.m_92750_(stack, string4, f4, (float)(this.y + 21 + (9 + 1) * 4), 0xFFFFFF);
        String string5 = I18n.m_118938_((String)"portality.gui.controller.link", (Object[])new Object[0]) + " " + (this.controller.isActive() ? I18n.m_118938_((String)"portality.gui.controller.link_active", (Object[])new Object[0]) : I18n.m_118938_((String)"portality.gui.controller.link_missing", (Object[])new Object[0]));
        float f5 = this.x + 10;
        Objects.requireNonNull(fontRenderer);
        fontRenderer.m_92750_(stack, string5, f5, (float)(this.y + 21 + (9 + 1) * 5), 0xFFFFFF);
        super.renderForeground(stack, mouseX, mouseY, partialTicks);
    }

    public List<IFactory<IScreenAddon>> guiAddons() {
        ArrayList<IFactory<IScreenAddon>> addons = new ArrayList<IFactory<IScreenAddon>>();
        addons.add(() -> new BasicScreenAddon(this.x - 25, this.y + 9){

            public int getXSize() {
                return 0;
            }

            public int getYSize() {
                return 0;
            }

            public void drawBackgroundLayer(PoseStack stack, Screen guiScreen, IAssetProvider iAssetProvider, int i, int i1, int i2, int i3, float v) {
                IBackgroundAsset background = (IBackgroundAsset)IAssetProvider.getAsset((IAssetProvider)PortalityAssetProvider.PROVIDER, (IAssetType)AssetTypes.BACKGROUND);
                RenderSystem.m_157456_((int)0, (ResourceLocation)background.getResourceLocation());
                guiScreen.m_93228_(stack, ControllerScreen.this.x - 25, ControllerScreen.this.y + 9, 0, 110, 25, 97);
            }

            public void drawForegroundLayer(PoseStack stack, Screen guiScreen, IAssetProvider iAssetProvider, int i, int i1, int i2, int i3, float partial) {
            }
        });
        this.controller.getScreenAddons().forEach(iFactory -> addons.add((IFactory<IScreenAddon>)iFactory));
        return addons;
    }

    public boolean m_7043_() {
        return false;
    }

    public ControllerTile getTile() {
        return this.controller;
    }
}

