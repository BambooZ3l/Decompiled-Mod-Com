/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.network.Message
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Registry
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraftforge.network.NetworkEvent$Context
 */
package com.buuz135.portality.network;

import com.buuz135.portality.tile.ControllerTile;
import com.hrznstudio.titanium.network.Message;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

public class PortalChangeColorMessage
extends Message {
    private ResourceLocation dimension;
    private BlockPos pos;
    private int color;

    public PortalChangeColorMessage(ResourceKey<Level> worldRegistryKey, BlockPos pos, int color) {
        this.dimension = worldRegistryKey.m_135782_();
        this.pos = pos;
        this.color = color;
    }

    public PortalChangeColorMessage() {
    }

    protected void handleMessage(NetworkEvent.Context context) {
        ServerLevel world = context.getSender().f_19853_.m_142572_().m_129880_(ResourceKey.m_135785_((ResourceKey)Registry.f_122819_, (ResourceLocation)this.dimension));
        BlockEntity tileEntity = world.m_7702_(this.pos);
        if (tileEntity instanceof ControllerTile) {
            ((ControllerTile)tileEntity).setColor(this.color);
        }
    }
}

