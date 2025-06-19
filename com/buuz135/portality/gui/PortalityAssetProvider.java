/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.api.client.AssetTypes
 *  com.hrznstudio.titanium.api.client.IAsset
 *  com.hrznstudio.titanium.api.client.IAssetType
 *  com.hrznstudio.titanium.api.client.assets.types.IBackgroundAsset
 *  com.hrznstudio.titanium.client.screen.asset.DefaultAssetProvider
 *  com.hrznstudio.titanium.client.screen.asset.IAssetProvider
 *  javax.annotation.Nullable
 *  net.minecraft.resources.ResourceLocation
 */
package com.buuz135.portality.gui;

import com.buuz135.portality.gui.button.PortalSettingButton;
import com.hrznstudio.titanium.api.client.AssetTypes;
import com.hrznstudio.titanium.api.client.IAsset;
import com.hrznstudio.titanium.api.client.IAssetType;
import com.hrznstudio.titanium.api.client.assets.types.IBackgroundAsset;
import com.hrznstudio.titanium.client.screen.asset.DefaultAssetProvider;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import java.awt.Point;
import java.awt.Rectangle;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;

public class PortalityAssetProvider
implements IAssetProvider {
    public static PortalityAssetProvider PROVIDER = new PortalityAssetProvider();
    private static ResourceLocation LOCATION = new ResourceLocation("portality", "textures/gui/controller.png");
    private final IBackgroundAsset BACKGROUND = new IBackgroundAsset(){

        public Point getInventoryPosition() {
            return new Point(0, 0);
        }

        public Point getHotbarPosition() {
            return new Point(0, 0);
        }

        public Rectangle getArea() {
            return new Rectangle(0, 0, 175, 110);
        }

        public ResourceLocation getResourceLocation() {
            return LOCATION;
        }
    };
    private final IAsset RENAME = new IAsset(){

        public Rectangle getArea() {
            return new Rectangle(176, 0, 20, 20);
        }

        public ResourceLocation getResourceLocation() {
            return LOCATION;
        }
    };
    private final IAsset PRIVATE = new IAsset(){

        public Rectangle getArea() {
            return new Rectangle(176, 21, 20, 20);
        }

        public ResourceLocation getResourceLocation() {
            return LOCATION;
        }
    };
    private final IAsset PUBLIC = new IAsset(){

        public Rectangle getArea() {
            return new Rectangle(197, 21, 20, 20);
        }

        public ResourceLocation getResourceLocation() {
            return LOCATION;
        }
    };
    private final IAsset NAME_HIDDEN = new IAsset(){

        public Rectangle getArea() {
            return new Rectangle(176, 42, 20, 20);
        }

        public ResourceLocation getResourceLocation() {
            return LOCATION;
        }
    };
    private final IAsset NAME_SHOWN = new IAsset(){

        public Rectangle getArea() {
            return new Rectangle(197, 42, 20, 20);
        }

        public ResourceLocation getResourceLocation() {
            return LOCATION;
        }
    };
    private final IAsset BOTH_DIRECTION = new IAsset(){

        public Rectangle getArea() {
            return new Rectangle(176, 63, 20, 20);
        }

        public ResourceLocation getResourceLocation() {
            return LOCATION;
        }
    };
    private final IAsset SEND = new IAsset(){

        public Rectangle getArea() {
            return new Rectangle(197, 63, 20, 20);
        }

        public ResourceLocation getResourceLocation() {
            return LOCATION;
        }
    };
    private final IAsset RECEIVE = new IAsset(){

        public Rectangle getArea() {
            return new Rectangle(218, 63, 20, 20);
        }

        public ResourceLocation getResourceLocation() {
            return LOCATION;
        }
    };
    private final IAsset CHANGE_COLOR = new IAsset(){

        public Rectangle getArea() {
            return new Rectangle(176, 84, 20, 20);
        }

        public ResourceLocation getResourceLocation() {
            return LOCATION;
        }
    };
    private final IAsset HUE_PICKER = new IAsset(){

        public Rectangle getArea() {
            return new Rectangle(235, 21, 9, 14);
        }

        public ResourceLocation getResourceLocation() {
            return new ResourceLocation("portality", "textures/gui/background.png");
        }
    };
    private final IAsset SHADER_PICKER = new IAsset(){

        public Rectangle getArea() {
            return new Rectangle(245, 21, 9, 9);
        }

        public ResourceLocation getResourceLocation() {
            return new ResourceLocation("portality", "textures/gui/background.png");
        }
    };

    @Nullable
    public <T extends IAsset> T getAsset(IAssetType<T> assetType) {
        if (assetType == AssetTypes.BACKGROUND) {
            return (T)assetType.castOrDefault((IAsset)this.BACKGROUND);
        }
        if (assetType == PortalSettingButton.RENAME) {
            return (T)assetType.castOrDefault(this.RENAME);
        }
        if (assetType == PortalSettingButton.PRIVATE) {
            return (T)assetType.castOrDefault(this.PRIVATE);
        }
        if (assetType == PortalSettingButton.PUBLIC) {
            return (T)assetType.castOrDefault(this.PUBLIC);
        }
        if (assetType == PortalSettingButton.NAME_HIDDEN) {
            return (T)assetType.castOrDefault(this.NAME_HIDDEN);
        }
        if (assetType == PortalSettingButton.NAME_SHOWN) {
            return (T)assetType.castOrDefault(this.NAME_SHOWN);
        }
        if (assetType == PortalSettingButton.BOTH_DIRECTION) {
            return (T)assetType.castOrDefault(this.BOTH_DIRECTION);
        }
        if (assetType == PortalSettingButton.SEND) {
            return (T)assetType.castOrDefault(this.SEND);
        }
        if (assetType == PortalSettingButton.RECEIVE) {
            return (T)assetType.castOrDefault(this.RECEIVE);
        }
        if (assetType == PortalSettingButton.CHANGE_COLOR) {
            return (T)assetType.castOrDefault(this.CHANGE_COLOR);
        }
        if (assetType == AssetTypes.HUE_PICKER) {
            return (T)assetType.castOrDefault(this.HUE_PICKER);
        }
        if (assetType == AssetTypes.SHADE_PICKER) {
            return (T)assetType.castOrDefault(this.SHADER_PICKER);
        }
        return (T)DefaultAssetProvider.DEFAULT_PROVIDER.getAsset(assetType);
    }
}

