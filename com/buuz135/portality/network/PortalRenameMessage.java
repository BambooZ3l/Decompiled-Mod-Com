/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.network.Message
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.network.NetworkEvent$Context
 */
package com.buuz135.portality.network;

import com.buuz135.portality.tile.ControllerTile;
import com.hrznstudio.titanium.network.Message;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

public class PortalRenameMessage
extends Message {
    private BlockPos tileLocation;
    private String name;

    public PortalRenameMessage() {
    }

    public PortalRenameMessage(String name, BlockPos tile) {
        this.name = name;
        this.tileLocation = tile;
    }

    protected void handleMessage(NetworkEvent.Context context) {
        ServerPlayer serverPlayer = context.getSender();
        context.enqueueWork(() -> {
            ControllerTile controller;
            Level world = serverPlayer.f_19853_;
            if (world.m_7702_(this.tileLocation) != null && world.m_7702_(this.tileLocation) instanceof ControllerTile && (controller = (ControllerTile)world.m_7702_(this.tileLocation)).getOwner().equals(serverPlayer.m_142081_())) {
                controller.setDisplayName(this.name);
            }
        });
    }
}

