/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.network.Message
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.network.NetworkEvent$Context
 */
package com.buuz135.portality.network;

import com.buuz135.portality.tile.ControllerTile;
import com.hrznstudio.titanium.network.Message;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

public class PortalDisplayToggleMessage
extends Message {
    private long tileLocation;

    public PortalDisplayToggleMessage() {
    }

    public PortalDisplayToggleMessage(long tileLocation) {
        this.tileLocation = tileLocation;
    }

    protected void handleMessage(NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            ControllerTile controller;
            Level world = context.getSender().f_19853_;
            BlockPos pos = BlockPos.m_122022_((long)this.tileLocation);
            if (world.m_7702_(pos) instanceof ControllerTile && (controller = (ControllerTile)world.m_7702_(pos)).getOwner().equals(context.getSender().m_142081_())) {
                controller.setDisplayNameEnabled(!controller.isDisplayNameEnabled());
            }
        });
    }
}

