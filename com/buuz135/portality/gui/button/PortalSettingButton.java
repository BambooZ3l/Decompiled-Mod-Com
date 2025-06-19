/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.Titanium
 *  com.hrznstudio.titanium.api.IFactory
 *  com.hrznstudio.titanium.api.client.GenericAssetType
 *  com.hrznstudio.titanium.api.client.IAsset
 *  com.hrznstudio.titanium.api.client.IAssetType
 *  com.hrznstudio.titanium.api.client.IScreenAddon
 *  com.hrznstudio.titanium.client.screen.ITileContainer
 *  com.hrznstudio.titanium.client.screen.ScreenAddonScreen
 *  com.hrznstudio.titanium.client.screen.addon.StateButtonAddon
 *  com.hrznstudio.titanium.client.screen.addon.StateButtonInfo
 *  com.hrznstudio.titanium.client.screen.asset.DefaultAssetProvider
 *  com.hrznstudio.titanium.client.screen.asset.IAssetProvider
 *  com.hrznstudio.titanium.component.button.ButtonComponent
 *  com.hrznstudio.titanium.network.locator.LocatorInstance
 *  com.hrznstudio.titanium.network.locator.instance.TileEntityLocatorInstance
 *  com.hrznstudio.titanium.network.messages.ButtonClickNetworkMessage
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package com.buuz135.portality.gui.button;

import com.hrznstudio.titanium.Titanium;
import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.client.GenericAssetType;
import com.hrznstudio.titanium.api.client.IAsset;
import com.hrznstudio.titanium.api.client.IAssetType;
import com.hrznstudio.titanium.api.client.IScreenAddon;
import com.hrznstudio.titanium.client.screen.ITileContainer;
import com.hrznstudio.titanium.client.screen.ScreenAddonScreen;
import com.hrznstudio.titanium.client.screen.addon.StateButtonAddon;
import com.hrznstudio.titanium.client.screen.addon.StateButtonInfo;
import com.hrznstudio.titanium.client.screen.asset.DefaultAssetProvider;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import com.hrznstudio.titanium.component.button.ButtonComponent;
import com.hrznstudio.titanium.network.locator.LocatorInstance;
import com.hrznstudio.titanium.network.locator.instance.TileEntityLocatorInstance;
import com.hrznstudio.titanium.network.messages.ButtonClickNetworkMessage;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class PortalSettingButton
extends ButtonComponent {
    public static final IAssetType<IAsset> RENAME = new GenericAssetType(arg_0 -> ((DefaultAssetProvider)IAssetProvider.DEFAULT_PROVIDER).getAsset(arg_0), IAsset.class);
    public static final IAssetType<IAsset> PRIVATE = new GenericAssetType(arg_0 -> ((DefaultAssetProvider)IAssetProvider.DEFAULT_PROVIDER).getAsset(arg_0), IAsset.class);
    public static final IAssetType<IAsset> PUBLIC = new GenericAssetType(arg_0 -> ((DefaultAssetProvider)IAssetProvider.DEFAULT_PROVIDER).getAsset(arg_0), IAsset.class);
    public static final IAssetType<IAsset> NAME_HIDDEN = new GenericAssetType(arg_0 -> ((DefaultAssetProvider)IAssetProvider.DEFAULT_PROVIDER).getAsset(arg_0), IAsset.class);
    public static final IAssetType<IAsset> NAME_SHOWN = new GenericAssetType(arg_0 -> ((DefaultAssetProvider)IAssetProvider.DEFAULT_PROVIDER).getAsset(arg_0), IAsset.class);
    public static final IAssetType<IAsset> BOTH_DIRECTION = new GenericAssetType(arg_0 -> ((DefaultAssetProvider)IAssetProvider.DEFAULT_PROVIDER).getAsset(arg_0), IAsset.class);
    public static final IAssetType<IAsset> SEND = new GenericAssetType(arg_0 -> ((DefaultAssetProvider)IAssetProvider.DEFAULT_PROVIDER).getAsset(arg_0), IAsset.class);
    public static final IAssetType<IAsset> RECEIVE = new GenericAssetType(arg_0 -> ((DefaultAssetProvider)IAssetProvider.DEFAULT_PROVIDER).getAsset(arg_0), IAsset.class);
    public static final IAssetType<IAsset> CHANGE_COLOR = new GenericAssetType(arg_0 -> ((DefaultAssetProvider)IAssetProvider.DEFAULT_PROVIDER).getAsset(arg_0), IAsset.class);
    private StateButtonInfo[] infos;
    private Supplier<Runnable> supplier;

    public PortalSettingButton(int posX, int posY, Supplier<Runnable> runnableSupplier, StateButtonInfo ... infos) {
        super(posX, posY, 20, 20);
        this.infos = infos;
        this.supplier = runnableSupplier;
    }

    @OnlyIn(value=Dist.CLIENT)
    public List<IFactory<? extends IScreenAddon>> getScreenAddons() {
        return Collections.singletonList(() -> new StateButtonAddon(this, this.infos){

            public int getState() {
                return PortalSettingButton.this.getState();
            }

            public boolean m_6375_(double mouseX, double mouseY, int button) {
                Screen screen = Minecraft.m_91087_().f_91080_;
                if (screen instanceof ScreenAddonScreen && screen instanceof ITileContainer) {
                    if (!this.m_5953_(mouseX - (double)((ScreenAddonScreen)screen).x, mouseY - (double)((ScreenAddonScreen)screen).y)) {
                        return false;
                    }
                    Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)new SimpleSoundInstance(SoundEvents.f_12490_, SoundSource.PLAYERS, 0.2f, 1.0f, Minecraft.m_91087_().f_91074_.m_142538_()));
                    Titanium.NETWORK.get().sendToServer((Object)new ButtonClickNetworkMessage((LocatorInstance)new TileEntityLocatorInstance(((ITileContainer)screen).getTile().m_58899_()), PortalSettingButton.this.getId(), new CompoundTag()));
                    PortalSettingButton.this.supplier.get().run();
                    return true;
                }
                return false;
            }
        });
    }

    public abstract int getState();

    public StateButtonInfo[] getInfos() {
        return this.infos;
    }
}

