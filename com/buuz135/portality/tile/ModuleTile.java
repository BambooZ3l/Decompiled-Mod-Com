/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.annotation.Save
 *  com.hrznstudio.titanium.api.IFactory
 *  com.hrznstudio.titanium.api.client.IScreenAddon
 *  com.hrznstudio.titanium.block.BasicTileBlock
 *  com.hrznstudio.titanium.client.screen.addon.StateButtonAddon
 *  com.hrznstudio.titanium.client.screen.addon.StateButtonInfo
 *  com.hrznstudio.titanium.client.screen.asset.IAssetProvider
 *  com.hrznstudio.titanium.component.button.ButtonComponent
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package com.buuz135.portality.tile;

import com.buuz135.portality.block.module.CapabilityModuleBlock;
import com.buuz135.portality.gui.TileAssetProvider;
import com.buuz135.portality.tile.FrameTile;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.client.IScreenAddon;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.client.screen.addon.StateButtonAddon;
import com.hrznstudio.titanium.client.screen.addon.StateButtonInfo;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import com.hrznstudio.titanium.component.button.ButtonComponent;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class ModuleTile<T extends ModuleTile<T>>
extends FrameTile<T> {
    @Save
    private boolean input = true;
    private ButtonComponent button = new ButtonComponent(153, 84, 14, 14){

        @OnlyIn(value=Dist.CLIENT)
        public List<IFactory<? extends IScreenAddon>> getScreenAddons() {
            return Collections.singletonList(() -> new StateButtonAddon(ModuleTile.this.button, new StateButtonInfo[]{new StateButtonInfo(0, TileAssetProvider.AA_BUTTON_IO_INPUT, new String[]{"module.type.input"}), new StateButtonInfo(1, TileAssetProvider.AA_BUTTON_IO_OUTPUT, new String[]{"module.type.output"})}){

                public int getState() {
                    return ModuleTile.this.input ? 0 : 1;
                }
            });
        }
    }.setPredicate((playerEntity, compoundNBT) -> this.changeMode());

    public ModuleTile(BasicTileBlock<T> base, BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(base, blockEntityType, pos, state);
        this.addButton(this.button);
    }

    public InteractionResult onActivated(Player playerIn, InteractionHand hand, Direction facing, double hitX, double hitY, double hitZ) {
        if (super.onActivated(playerIn, hand, facing, hitX, hitY, hitZ) == InteractionResult.SUCCESS) {
            return InteractionResult.SUCCESS;
        }
        this.openGui(playerIn);
        return InteractionResult.SUCCESS;
    }

    public void changeMode() {
        this.input = !this.input;
        this.f_58857_.m_46597_(this.f_58858_, (BlockState)this.m_58900_().m_61124_((Property)CapabilityModuleBlock.INPUT, (Comparable)Boolean.valueOf(this.input)));
        this.markForUpdate();
    }

    public IAssetProvider getAssetProvider() {
        return TileAssetProvider.PROVIDER;
    }

    public boolean isInput() {
        return this.input;
    }
}

