/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.Level
 */
package com.buuz135.portality.handler;

import com.buuz135.portality.tile.ControllerTile;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

public class ChunkLoaderHandler {
    public static void removePortalAsChunkloader(ControllerTile controller) {
        Level world = controller.m_58904_();
        if (world instanceof ServerLevel) {
            ChunkPos chunkPos = world.m_46745_(controller.m_58899_()).m_7697_();
            ((ServerLevel)world).m_8602_(chunkPos.f_45578_, chunkPos.f_45579_, false);
        }
    }

    public static void addPortalAsChunkloader(ControllerTile controller) {
        Level world = controller.m_58904_();
        if (world instanceof ServerLevel) {
            ChunkPos chunkPos = world.m_46745_(controller.m_58899_()).m_7697_();
            if (!((ServerLevel)world).m_8902_().contains(chunkPos.m_45588_())) {
                ((ServerLevel)world).m_8602_(chunkPos.f_45578_, chunkPos.f_45579_, true);
                if (!world.isAreaLoaded(controller.m_58899_(), 2)) {
                    world.m_46745_(controller.m_58899_()).m_62913_(true);
                }
            }
        }
    }
}

