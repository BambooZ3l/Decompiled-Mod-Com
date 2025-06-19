/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.network.Message
 *  net.minecraft.client.Minecraft
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Registry
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraftforge.network.NetworkDirection
 *  net.minecraftforge.network.NetworkEvent$Context
 */
package com.buuz135.portality.network;

import com.buuz135.portality.Portality;
import com.buuz135.portality.data.PortalDataManager;
import com.buuz135.portality.data.PortalInformation;
import com.buuz135.portality.data.TokenPortalInformation;
import com.buuz135.portality.gui.PortalsScreen;
import com.buuz135.portality.tile.ControllerTile;
import com.buuz135.portality.util.BlockPosUtils;
import com.hrznstudio.titanium.network.Message;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class PortalNetworkMessage {
    public static void sendInformationToPlayer(ServerPlayer playerEntity, boolean interdimensional, BlockPos pos, int distance, HashMap<String, CompoundTag> tokens) {
        ArrayList<PortalInformation> infos = new ArrayList<PortalInformation>();
        tokens.forEach((s, compoundNBT) -> infos.add(new TokenPortalInformation(playerEntity.m_142081_(), (ResourceKey<Level>)ResourceKey.m_135785_((ResourceKey)Registry.f_122819_, (ResourceLocation)new ResourceLocation(compoundNBT.m_128461_("Dimension"))), new BlockPos(compoundNBT.m_128451_("X"), compoundNBT.m_128451_("Y"), compoundNBT.m_128451_("Z")), (String)s)));
        infos.addAll(PortalDataManager.getData((LevelAccessor)playerEntity.f_19853_).getInformationList());
        infos.removeIf(information -> information.getDimension().equals((Object)playerEntity.m_183503_().m_46472_()) && information.getLocation().equals((Object)pos));
        infos.removeIf(information -> {
            ServerLevel world = playerEntity.m_20194_().m_129880_(information.getDimension());
            return world.m_7702_(information.getLocation()) instanceof ControllerTile && !((ControllerTile)world.m_7702_(information.getLocation())).isFormed();
        });
        infos.removeIf(information -> !interdimensional && !playerEntity.m_183503_().m_46472_().equals(information.getDimension()));
        infos.removeIf(information -> interdimensional && !playerEntity.m_183503_().m_46472_().equals(information.getDimension()) && !information.isInterdimensional());
        infos.removeIf(information -> {
            ServerLevel world = playerEntity.m_20193_().m_142572_().m_129880_(information.getDimension());
            BlockEntity entity = world.m_7702_(information.getLocation());
            return entity instanceof ControllerTile && !interdimensional && (!playerEntity.m_183503_().m_46472_().equals(information.getDimension()) || !information.getLocation().m_123314_((Vec3i)pos, (double)distance) || !information.getLocation().m_123314_((Vec3i)pos, (double)BlockPosUtils.getMaxDistance(((ControllerTile)entity).getLength())));
        });
        Portality.NETWORK.get().sendTo((Object)new Response(infos), playerEntity.f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static class Response
    extends Message {
        private CompoundTag compoundNBT = new CompoundTag();

        public Response() {
        }

        public Response(List<PortalInformation> information) {
            information.forEach(portalInformation -> this.compoundNBT.m_128365_(portalInformation.getId().toString(), (Tag)portalInformation.writetoNBT()));
        }

        protected void handleMessage(NetworkEvent.Context context) {
            Minecraft.m_91087_().m_6937_(() -> {
                if (Minecraft.m_91087_().f_91080_ instanceof PortalsScreen) {
                    ArrayList<PortalInformation> information = new ArrayList<PortalInformation>();
                    this.compoundNBT.m_128431_().forEach(s -> information.add(PortalInformation.readFromNBT(this.compoundNBT.m_128469_(s))));
                    ((PortalsScreen)Minecraft.m_91087_().f_91080_).refresh(information);
                }
            });
        }
    }
}

