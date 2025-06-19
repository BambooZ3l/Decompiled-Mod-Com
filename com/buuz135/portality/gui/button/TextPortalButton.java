/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.Titanium
 *  com.hrznstudio.titanium.api.IFactory
 *  com.hrznstudio.titanium.api.client.IScreenAddon
 *  com.hrznstudio.titanium.client.screen.ITileContainer
 *  com.hrznstudio.titanium.client.screen.ScreenAddonScreen
 *  com.hrznstudio.titanium.client.screen.addon.BasicButtonAddon
 *  com.hrznstudio.titanium.client.screen.asset.IAssetProvider
 *  com.hrznstudio.titanium.component.button.ButtonComponent
 *  com.hrznstudio.titanium.network.locator.LocatorInstance
 *  com.hrznstudio.titanium.network.locator.instance.TileEntityLocatorInstance
 *  com.hrznstudio.titanium.network.messages.ButtonClickNetworkMessage
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.TranslatableComponent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package com.buuz135.portality.gui.button;

import com.hrznstudio.titanium.Titanium;
import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.client.IScreenAddon;
import com.hrznstudio.titanium.client.screen.ITileContainer;
import com.hrznstudio.titanium.client.screen.ScreenAddonScreen;
import com.hrznstudio.titanium.client.screen.addon.BasicButtonAddon;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import com.hrznstudio.titanium.component.button.ButtonComponent;
import com.hrznstudio.titanium.network.locator.LocatorInstance;
import com.hrznstudio.titanium.network.locator.instance.TileEntityLocatorInstance;
import com.hrznstudio.titanium.network.messages.ButtonClickNetworkMessage;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TextPortalButton
extends ButtonComponent {
    private final String display;
    private Supplier<Consumer<Screen>> screenConsumer;

    public TextPortalButton(int posX, int posY, int sizeX, int sizeY, String display) {
        super(posX, posY, sizeX, sizeY);
        this.display = display;
        this.screenConsumer = () -> screen -> {};
    }

    public String getDisplay() {
        return this.display;
    }

    public TextPortalButton setClientConsumer(Supplier<Consumer<Screen>> screenConsumer) {
        this.screenConsumer = screenConsumer;
        return this;
    }

    @OnlyIn(value=Dist.CLIENT)
    public List<IFactory<? extends IScreenAddon>> getScreenAddons() {
        return Collections.singletonList(() -> new TextButtonAddon(this, this.display, this.screenConsumer.get()));
    }

    @OnlyIn(value=Dist.CLIENT)
    public class TextButtonAddon
    extends BasicButtonAddon {
        private String text;
        private Consumer<Screen> supplier;

        public TextButtonAddon(ButtonComponent posButton, String text, Consumer<Screen> supplier) {
            super(posButton);
            this.text = text;
            this.supplier = supplier;
        }

        public void drawBackgroundLayer(PoseStack stack, Screen screen, IAssetProvider provider, int guiX, int guiY, int mouseX, int mouseY, float partialTicks) {
            super.drawBackgroundLayer(stack, screen, provider, guiX, guiY, mouseX, mouseY, partialTicks);
            String string = new TranslatableComponent(this.text).getString();
            ChatFormatting color = this.m_5953_(mouseX - guiX, mouseY - guiY) ? ChatFormatting.YELLOW : ChatFormatting.WHITE;
            Minecraft.m_91087_().f_91062_.m_92883_(stack, color + string, (float)(guiX + this.getPosX() + this.getXSize() / 2 - Minecraft.m_91087_().f_91062_.m_92895_(string) / 2), (float)(guiY + this.getPosY()) + (float)this.getYSize() / 2.0f - 3.5f, 0xFFFFFF);
        }

        public boolean m_6375_(double mouseX, double mouseY, int button) {
            Screen screen = Minecraft.m_91087_().f_91080_;
            if (screen instanceof ScreenAddonScreen && screen instanceof ITileContainer) {
                if (!this.m_5953_(mouseX - (double)((ScreenAddonScreen)screen).x, mouseY - (double)((ScreenAddonScreen)screen).y)) {
                    return false;
                }
                Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)new SimpleSoundInstance(SoundEvents.f_12490_, SoundSource.PLAYERS, 0.2f, 1.0f, Minecraft.m_91087_().f_91074_.m_142538_()));
                Titanium.NETWORK.get().sendToServer((Object)new ButtonClickNetworkMessage((LocatorInstance)new TileEntityLocatorInstance(((ITileContainer)screen).getTile().m_58899_()), TextPortalButton.this.getId(), new CompoundTag()));
                this.supplier.accept(screen);
                return true;
            }
            return false;
        }
    }
}

