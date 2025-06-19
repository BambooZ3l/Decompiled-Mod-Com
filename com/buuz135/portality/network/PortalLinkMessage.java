/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.network.CompoundSerializableDataHandler
 *  com.hrznstudio.titanium.network.Message
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraftforge.network.NetworkEvent$Context
 */
package com.buuz135.portality.network;

import com.buuz135.portality.data.PortalLinkData;
import com.buuz135.portality.tile.ControllerTile;
import com.hrznstudio.titanium.network.CompoundSerializableDataHandler;
import com.hrznstudio.titanium.network.Message;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

public class PortalLinkMessage
extends Message {
    private int type;
    private PortalLinkData linkSender;
    private PortalLinkData linkReceiver;

    public PortalLinkMessage() {
    }

    public PortalLinkMessage(int type, PortalLinkData linkSender, PortalLinkData linkReceiver) {
        this.type = type;
        this.linkSender = linkSender;
        this.linkReceiver = linkReceiver;
    }

    protected void handleMessage(NetworkEvent.Context context) {
        ServerLevel world = context.getSender().f_19853_.m_142572_().m_129880_(this.linkSender.getDimension());
        BlockEntity tileEntity = world.m_7702_(this.linkSender.getPos());
        if (tileEntity instanceof ControllerTile) {
            ((ControllerTile)tileEntity).linkTo(new PortalLinkData(this.linkReceiver.getDimension(), this.linkReceiver.getPos(), true, this.linkSender.getName(), this.linkReceiver.isToken()), PortalLinkData.PortalCallType.values()[this.type]);
        }
    }

    static {
        CompoundSerializableDataHandler.map(PortalLinkData.class, buf -> PortalLinkData.readFromNBT(buf.m_130260_()), (buf, portalLinkData) -> buf.m_130079_(portalLinkData.writeToNBT()));
    }
}

