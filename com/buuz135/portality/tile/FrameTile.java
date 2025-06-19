/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.block.BasicTileBlock
 *  com.hrznstudio.titanium.block.tile.ActiveTile
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package com.buuz135.portality.tile;

import com.buuz135.portality.proxy.client.IPortalColor;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.block.tile.ActiveTile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class FrameTile<T extends FrameTile<T>>
extends ActiveTile<T>
implements IPortalColor {
    private BlockPos controllerPos;
    private int color = Integer.parseInt("0094ff", 16);

    public FrameTile(BasicTileBlock<T> base, BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(base, blockEntityType, pos, state);
    }

    protected void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        if (this.controllerPos != null) {
            compoundTag.m_128405_("X", this.controllerPos.m_123341_());
            compoundTag.m_128405_("Y", this.controllerPos.m_123342_());
            compoundTag.m_128405_("Z", this.controllerPos.m_123343_());
        }
        compoundTag.m_128405_("Color", this.color);
    }

    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        if (compound.m_128441_("X")) {
            this.controllerPos = new BlockPos(compound.m_128451_("X"), compound.m_128451_("Y"), compound.m_128451_("Z"));
        }
        if (compound.m_128441_("Color")) {
            if (this.color != compound.m_128451_("Color") && this.f_58857_ != null) {
                this.f_58857_.m_7260_(this.f_58858_, this.m_58900_(), this.m_58900_(), 1);
            }
            this.color = compound.m_128451_("Color");
        }
    }

    public BlockPos getControllerPos() {
        return this.controllerPos;
    }

    public void setControllerPos(BlockPos controllerPos) {
        this.controllerPos = controllerPos;
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
        this.markForUpdate();
    }
}

