/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.network.Message
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.core.Direction
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.network.NetworkEvent$Context
 */
package com.buuz135.portality.network;

import com.buuz135.portality.proxy.PortalityConfig;
import com.buuz135.portality.proxy.PortalitySoundHandler;
import com.hrznstudio.titanium.network.Message;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

public class PortalTeleportMessage
extends Message {
    private int facing;
    private int length;

    public PortalTeleportMessage(int facing, int length) {
        this.facing = facing;
        this.length = length;
    }

    public PortalTeleportMessage() {
    }

    protected void handleMessage(NetworkEvent.Context context) {
        Minecraft.m_91087_().m_18689_(() -> {
            Minecraft.m_91087_().f_91074_.m_5496_((SoundEvent)PortalitySoundHandler.PORTAL_TP.get(), 0.1f, 1.0f);
            if (PortalityConfig.LAUNCH_PLAYERS) {
                Direction facing = Direction.values()[this.facing];
                Vec3 vector = new Vec3((double)facing.m_122436_().m_123341_(), (double)facing.m_122436_().m_123342_(), (double)facing.m_122436_().m_123343_()).m_82490_((double)(2 * this.length) / (double)PortalityConfig.MAX_PORTAL_LENGTH);
                LocalPlayer player = Minecraft.m_91087_().f_91074_;
                player.m_20334_(vector.f_82479_, vector.f_82480_, vector.f_82481_);
            }
        });
    }
}

