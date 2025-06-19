/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.properties.Property
 */
package com.buuz135.portality.handler;

import com.buuz135.portality.block.ControllerBlock;
import com.buuz135.portality.block.module.IPortalModule;
import com.buuz135.portality.proxy.PortalityConfig;
import com.buuz135.portality.tile.ControllerTile;
import com.buuz135.portality.tile.FrameTile;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.Property;

public class StructureHandler {
    private int length = 0;
    private int width = 0;
    private int height = 0;
    private ControllerTile controller;
    private List<BlockPos> modules = new ArrayList<BlockPos>();
    private List<BlockPos> frameBlocks = new ArrayList<BlockPos>();
    private boolean shouldCheckForStructure;

    public StructureHandler(ControllerTile controllerTile) {
        this.controller = controllerTile;
        this.shouldCheckForStructure = true;
    }

    public boolean checkArea() {
        this.checkPortalSize();
        if (this.length < 3) {
            return false;
        }
        Direction facing = (Direction)this.controller.m_58904_().m_8055_(this.controller.m_58899_()).m_61143_((Property)ControllerBlock.FACING_HORIZONTAL);
        this.modules.clear();
        if (!this.checkFramesInTheBox(this.controller.m_58899_().m_5484_(facing.m_122427_(), this.width), this.controller.m_58899_().m_5484_(facing.m_122428_(), this.width).m_5484_(facing.m_122424_(), this.length - 1), false)) {
            return false;
        }
        if (!this.checkFramesInTheBox(this.controller.m_58899_().m_5484_(facing.m_122427_(), this.width).m_5484_(Direction.UP, this.height - 1), this.controller.m_58899_().m_5484_(facing.m_122428_(), this.width).m_5484_(facing.m_122424_(), this.length - 1).m_5484_(Direction.UP, this.height - 1), false)) {
            return false;
        }
        if (!this.checkFramesInTheBox(this.controller.m_58899_().m_5484_(facing.m_122427_(), this.width).m_5484_(Direction.UP, 1), this.controller.m_58899_().m_5484_(facing.m_122427_(), this.width).m_5484_(Direction.UP, this.height - 2).m_5484_(facing.m_122424_(), this.length - 1), false)) {
            return false;
        }
        if (!this.checkFramesInTheBox(this.controller.m_58899_().m_5484_(facing.m_122428_(), this.width).m_5484_(Direction.UP, 1), this.controller.m_58899_().m_5484_(facing.m_122428_(), this.width).m_5484_(Direction.UP, this.height - 2).m_5484_(facing.m_122424_(), this.length - 1), false)) {
            return false;
        }
        this.checkFramesInTheBox(this.controller.m_58899_().m_5484_(facing.m_122427_(), this.width), this.controller.m_58899_().m_5484_(facing.m_122428_(), this.width).m_5484_(facing.m_122424_(), this.length - 1), true);
        this.checkFramesInTheBox(this.controller.m_58899_().m_5484_(facing.m_122427_(), this.width).m_5484_(Direction.UP, this.height - 1), this.controller.m_58899_().m_5484_(facing.m_122428_(), this.width).m_5484_(facing.m_122424_(), this.length - 1).m_5484_(Direction.UP, this.height - 1), true);
        this.checkFramesInTheBox(this.controller.m_58899_().m_5484_(facing.m_122427_(), this.width).m_5484_(Direction.UP, 1), this.controller.m_58899_().m_5484_(facing.m_122427_(), this.width).m_5484_(Direction.UP, this.height - 2).m_5484_(facing.m_122424_(), this.length - 1), true);
        this.checkFramesInTheBox(this.controller.m_58899_().m_5484_(facing.m_122428_(), this.width).m_5484_(Direction.UP, 1), this.controller.m_58899_().m_5484_(facing.m_122428_(), this.width).m_5484_(Direction.UP, this.height - 2).m_5484_(facing.m_122424_(), this.length - 1), true);
        return true;
    }

    public boolean checkFramesInTheBox(BlockPos point1, BlockPos point2, boolean save) {
        for (BlockPos blockPos : BlockPos.m_121940_((BlockPos)point1, (BlockPos)point2)) {
            BlockEntity entity;
            if (!blockPos.equals((Object)this.controller.m_58899_()) && !this.isValidFrame(blockPos)) {
                return false;
            }
            if (!save) continue;
            this.frameBlocks.add(blockPos.m_7949_());
            if (this.controller.m_58904_().m_8055_(blockPos).m_60734_() instanceof IPortalModule) {
                this.modules.add(blockPos.m_7949_());
            }
            if (!((entity = this.controller.m_58904_().m_7702_(blockPos)) instanceof FrameTile)) continue;
            ((FrameTile)entity).setControllerPos(this.controller.m_58899_());
            ((FrameTile)entity).setColor(this.controller.getColor());
            entity.m_6596_();
        }
        return true;
    }

    private void checkPortalSize() {
        int length;
        int height;
        int width;
        Direction controllerFacing = (Direction)this.controller.m_58904_().m_8055_(this.controller.m_58899_()).m_61143_((Property)ControllerBlock.FACING_HORIZONTAL);
        if (controllerFacing.m_122434_().m_122478_()) {
            return;
        }
        Direction widthFacing = controllerFacing.m_122427_();
        for (width = 1; this.isValidFrame(this.controller.m_58899_().m_5484_(widthFacing, width)) && !this.isValidFrame(this.controller.m_58899_().m_5484_(widthFacing, width).m_142300_(Direction.UP)) && width <= PortalityConfig.MAX_PORTAL_WIDTH; ++width) {
        }
        for (height = 1; this.isValidFrame(this.controller.m_58899_().m_5484_(widthFacing, width).m_5484_(Direction.UP, height)) && height <= PortalityConfig.MAX_PORTAL_HEIGHT; ++height) {
        }
        Direction lengthChecking = controllerFacing.m_122424_();
        for (length = 1; this.isValidFrame(this.controller.m_58899_().m_5484_(lengthChecking, length)) && length <= PortalityConfig.MAX_PORTAL_LENGTH; ++length) {
        }
        this.width = width;
        this.height = height;
        this.length = length;
    }

    private boolean isValidFrame(BlockPos pos) {
        return this.controller.m_58904_().m_7702_(pos) instanceof FrameTile && (((FrameTile)this.controller.m_58904_().m_7702_(pos)).getControllerPos() == null || ((FrameTile)this.controller.m_58904_().m_7702_(pos)).getControllerPos().equals((Object)this.controller.m_58899_()));
    }

    public void cancelFrameBlocks() {
        for (BlockPos frameBlock : this.frameBlocks) {
            BlockEntity entity = this.controller.m_58904_().m_7702_(frameBlock);
            if (!(entity instanceof FrameTile)) continue;
            ((FrameTile)entity).setControllerPos(null);
            entity.m_6596_();
        }
        this.frameBlocks.clear();
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean shouldCheckForStructure() {
        return this.shouldCheckForStructure;
    }

    public void setShouldCheckForStructure(boolean shouldCheckForStructure) {
        this.shouldCheckForStructure = shouldCheckForStructure;
    }

    public List<BlockPos> getModules() {
        return this.modules;
    }

    public List<BlockPos> getFrameBlocks() {
        return this.frameBlocks;
    }
}

