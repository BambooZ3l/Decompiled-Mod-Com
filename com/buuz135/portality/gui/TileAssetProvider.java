/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.api.client.AssetTypes
 *  com.hrznstudio.titanium.api.client.GenericAssetType
 *  com.hrznstudio.titanium.api.client.IAsset
 *  com.hrznstudio.titanium.api.client.IAssetType
 *  com.hrznstudio.titanium.api.client.assets.types.IBackgroundAsset
 *  com.hrznstudio.titanium.api.client.assets.types.ITankAsset
 *  com.hrznstudio.titanium.client.screen.asset.DefaultAssetProvider
 *  com.hrznstudio.titanium.client.screen.asset.IAssetProvider
 *  javax.annotation.Nullable
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 */
package com.buuz135.portality.gui;

import com.hrznstudio.titanium.api.client.AssetTypes;
import com.hrznstudio.titanium.api.client.GenericAssetType;
import com.hrznstudio.titanium.api.client.IAsset;
import com.hrznstudio.titanium.api.client.IAssetType;
import com.hrznstudio.titanium.api.client.assets.types.IBackgroundAsset;
import com.hrznstudio.titanium.api.client.assets.types.ITankAsset;
import com.hrznstudio.titanium.client.screen.asset.DefaultAssetProvider;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import java.awt.Point;
import java.awt.Rectangle;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public final class TileAssetProvider
implements IAssetProvider {
    public static TileAssetProvider PROVIDER = new TileAssetProvider();
    private static final ResourceLocation LOCATION = new ResourceLocation("portality", "textures/gui/background.png");
    private final Point HOTBAR_POS = new Point(8, 160);
    private final Point INV_POS = new Point(8, 102);
    private final IAsset PROGRESS_BAR_BORDER = new IAsset(){

        public Rectangle getArea() {
            return new Rectangle(211, 1, 11, 56);
        }

        public ResourceLocation getResourceLocation() {
            return LOCATION;
        }
    };
    private final IAsset SLOT = new IAsset(){

        public Rectangle getArea() {
            return new Rectangle(1, 185, 18, 18);
        }

        public ResourceLocation getResourceLocation() {
            return LOCATION;
        }
    };
    private final IAsset ENERGY_BAR = new IAsset(){

        public Rectangle getArea() {
            return new Rectangle(177, 94, 18, 46);
        }

        public ResourceLocation getResourceLocation() {
            return LOCATION;
        }
    };
    private final IAsset ENERGY_FILL = new IAsset(){

        public Rectangle getArea() {
            return new Rectangle(196, 97, 12, 40);
        }

        public Point getOffset() {
            return new Point(3, 3);
        }

        public ResourceLocation getResourceLocation() {
            return LOCATION;
        }
    };
    private final ITankAsset TANK = new ITankAsset(){

        public int getFluidRenderPadding(Direction facing) {
            return 3;
        }

        public Rectangle getArea() {
            return new Rectangle(177, 1, 18, 56);
        }

        public ResourceLocation getResourceLocation() {
            return LOCATION;
        }
    };
    private final IBackgroundAsset BACKGROUND = new IBackgroundAsset(){

        public Point getInventoryPosition() {
            return TileAssetProvider.this.INV_POS;
        }

        public Point getHotbarPosition() {
            return TileAssetProvider.this.HOTBAR_POS;
        }

        public Rectangle getArea() {
            return new Rectangle(0, 0, 176, 184);
        }

        public ResourceLocation getResourceLocation() {
            return LOCATION;
        }
    };
    private final IAsset PROGRESS_BAR_BACKGROUND = new IAsset(){

        public Rectangle getArea() {
            return new Rectangle(229, 1, 5, 50);
        }

        public Point getOffset() {
            return new Point(3, 3);
        }

        public ResourceLocation getResourceLocation() {
            return LOCATION;
        }
    };
    private final IAsset PROGRESS_BAR_FILL = new IAsset(){

        public Rectangle getArea() {
            return new Rectangle(223, 1, 5, 50);
        }

        public Point getOffset() {
            return new Point(3, 3);
        }

        public ResourceLocation getResourceLocation() {
            return LOCATION;
        }
    };
    private final IAsset BUTTON_SIDENESS_DISABLED = new IAsset(){

        public Rectangle getArea() {
            return new Rectangle(196, 1, 14, 14);
        }

        public ResourceLocation getResourceLocation() {
            return LOCATION;
        }
    };
    private final IAsset BUTTON_SIDENESS_ENABLED = new IAsset(){

        public Rectangle getArea() {
            return new Rectangle(196, 16, 14, 14);
        }

        public ResourceLocation getResourceLocation() {
            return LOCATION;
        }
    };
    private final IAsset BUTTON_SIDENESS_PULL = new IAsset(){

        public Rectangle getArea() {
            return new Rectangle(196, 31, 14, 14);
        }

        public ResourceLocation getResourceLocation() {
            return LOCATION;
        }
    };
    private final IAsset BUTTON_SIDENESS_PUSH = new IAsset(){

        public Rectangle getArea() {
            return new Rectangle(196, 46, 14, 14);
        }

        public ResourceLocation getResourceLocation() {
            return LOCATION;
        }
    };
    public static final IAssetType<IAsset> AA_BUTTON_IO_INPUT = new GenericAssetType(arg_0 -> ((DefaultAssetProvider)IAssetProvider.DEFAULT_PROVIDER).getAsset(arg_0), IAsset.class);
    public static final IAssetType<IAsset> AA_BUTTON_IO_OUTPUT = new GenericAssetType(arg_0 -> ((DefaultAssetProvider)IAssetProvider.DEFAULT_PROVIDER).getAsset(arg_0), IAsset.class);
    private final IAsset BUTTON_SIDENESS_MANAGER = new IAsset(){

        public Rectangle getArea() {
            return new Rectangle(1, 231, 14, 14);
        }

        public ResourceLocation getResourceLocation() {
            return LOCATION;
        }
    };
    private final IAsset BUTTON_IO_INPUT = new IAsset(){

        public Rectangle getArea() {
            return new Rectangle(178, 141, 14, 14);
        }

        public ResourceLocation getResourceLocation() {
            return LOCATION;
        }
    };
    private final IAsset BUTTON_IO_OUTPUT = new IAsset(){

        public Rectangle getArea() {
            return new Rectangle(178, 156, 14, 14);
        }

        public ResourceLocation getResourceLocation() {
            return LOCATION;
        }
    };

    TileAssetProvider() {
    }

    @Nullable
    public <T extends IAsset> T getAsset(IAssetType<T> assetType) {
        if (assetType == AssetTypes.BACKGROUND) {
            return (T)assetType.castOrDefault((IAsset)this.BACKGROUND);
        }
        if (assetType == AssetTypes.ENERGY_BACKGROUND) {
            return (T)assetType.castOrDefault(this.ENERGY_BAR);
        }
        if (assetType == AssetTypes.ENERGY_BAR) {
            return (T)assetType.castOrDefault(this.ENERGY_FILL);
        }
        if (assetType == AssetTypes.PROGRESS_BAR_BACKGROUND_VERTICAL) {
            return (T)assetType.castOrDefault(this.PROGRESS_BAR_BACKGROUND);
        }
        if (assetType == AssetTypes.PROGRESS_BAR_VERTICAL) {
            return (T)assetType.castOrDefault(this.PROGRESS_BAR_FILL);
        }
        if (assetType == AssetTypes.SLOT) {
            return (T)assetType.castOrDefault(this.SLOT);
        }
        if (assetType == AssetTypes.TANK_NORMAL) {
            return (T)assetType.castOrDefault((IAsset)this.TANK);
        }
        if (assetType == AssetTypes.PROGRESS_BAR_BORDER_VERTICAL) {
            return (T)assetType.castOrDefault(this.PROGRESS_BAR_BORDER);
        }
        if (assetType == AssetTypes.BUTTON_SIDENESS_DISABLED) {
            return (T)assetType.castOrDefault(this.BUTTON_SIDENESS_DISABLED);
        }
        if (assetType == AssetTypes.BUTTON_SIDENESS_ENABLED) {
            return (T)assetType.castOrDefault(this.BUTTON_SIDENESS_ENABLED);
        }
        if (assetType == AssetTypes.BUTTON_SIDENESS_PULL) {
            return (T)assetType.castOrDefault(this.BUTTON_SIDENESS_PULL);
        }
        if (assetType == AssetTypes.BUTTON_SIDENESS_PUSH) {
            return (T)assetType.castOrDefault(this.BUTTON_SIDENESS_PUSH);
        }
        if (assetType == AssetTypes.BUTTON_SIDENESS_MANAGER) {
            return (T)assetType.castOrDefault(this.BUTTON_SIDENESS_MANAGER);
        }
        if (assetType == AA_BUTTON_IO_INPUT) {
            return (T)assetType.castOrDefault(this.BUTTON_IO_INPUT);
        }
        if (assetType == AA_BUTTON_IO_OUTPUT) {
            return (T)assetType.castOrDefault(this.BUTTON_IO_OUTPUT);
        }
        return null;
    }
}

