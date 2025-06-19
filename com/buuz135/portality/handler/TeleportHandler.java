/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.util.TeleportationUtils
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundCustomSoundPacket
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.network.NetworkDirection
 */
package com.buuz135.portality.handler;

import com.buuz135.portality.Portality;
import com.buuz135.portality.block.ControllerBlock;
import com.buuz135.portality.data.PortalLinkData;
import com.buuz135.portality.network.PortalTeleportMessage;
import com.buuz135.portality.proxy.PortalityConfig;
import com.buuz135.portality.proxy.PortalitySoundHandler;
import com.buuz135.portality.tile.ControllerTile;
import com.hrznstudio.titanium.util.TeleportationUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundCustomSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkDirection;

public class TeleportHandler {
    private HashMap<Entity, TeleportData> entityTimeToTeleport = new HashMap();
    private HashMap<Entity, TeleportedEntityData> entitesTeleported = new HashMap();
    private ControllerTile controller;

    public TeleportHandler(ControllerTile controller) {
        this.controller = controller;
    }

    public void addEntityToTeleport(Entity entity, PortalLinkData data) {
        if (!this.entityTimeToTeleport.containsKey(entity)) {
            this.entityTimeToTeleport.put(entity, new TeleportData(data));
        }
    }

    public void tick() {
        if (!(this.controller.m_58904_().m_8055_(this.controller.m_58899_()).m_60734_() instanceof ControllerBlock)) {
            this.controller.closeLink();
            return;
        }
        Direction facing = ((Direction)this.controller.m_58904_().m_8055_(this.controller.m_58899_()).m_61143_((Property)ControllerBlock.FACING_HORIZONTAL)).m_122424_();
        Random random = this.controller.m_58904_().f_46441_;
        BlockPos offset = this.controller.m_58899_().m_142300_(facing);
        double mult = (double)this.controller.getLength() / 20.0;
        this.controller.m_58904_().m_7106_((ParticleOptions)ParticleTypes.f_123810_, (double)offset.m_123341_() + 0.5 + random.nextDouble() * (double)(this.controller.getWidth() + 2) - (double)(this.controller.getWidth() + 2) / 2.0, (double)offset.m_123342_() + (double)this.controller.getHeight() / 2.0 + random.nextDouble() * (double)(this.controller.getHeight() - 2) - (double)(this.controller.getHeight() - 2) / 2.0, (double)offset.m_123343_() + 0.5 + random.nextDouble() * 2.0 - 1.0, (double)facing.m_122436_().m_123341_() * mult, (double)facing.m_122436_().m_123342_() * mult, (double)facing.m_122436_().m_123343_() * mult);
        ArrayList<Entity> entityRemove = new ArrayList<Entity>();
        for (Map.Entry<Entity, TeleportData> entry : this.entityTimeToTeleport.entrySet()) {
            if (!entry.getKey().m_6084_() || !this.controller.m_58904_().m_45976_(Entity.class, this.controller.getPortalArea()).contains(entry.getKey())) {
                entityRemove.add(entry.getKey());
                continue;
            }
            if (entry.getKey() instanceof Player && entry.getKey().m_6047_()) {
                entityRemove.add(entry.getKey());
                continue;
            }
            BlockPos destinationPos = this.controller.m_58899_().m_142022_(0.5, (double)this.controller.getHeight() / 2.0 - 0.75, 0.5).m_5484_(facing, this.controller.getLength() - 1);
            Vec3 destination = new Vec3((double)destinationPos.m_123341_(), (double)destinationPos.m_123342_(), (double)destinationPos.m_123343_()).m_82520_(0.5, 0.0, 0.5);
            double distance = destinationPos.m_123333_(new Vec3i(entry.getKey().m_142538_().m_123341_(), entry.getKey().m_142538_().m_123342_(), entry.getKey().m_142538_().m_123343_()));
            destination = destination.m_82492_((double)entry.getKey().m_142538_().m_123341_(), (double)entry.getKey().m_142538_().m_123342_(), (double)entry.getKey().m_142538_().m_123343_()).m_82490_((entry.getValue().time += 0.05) / distance);
            if (destinationPos.m_123314_(new Vec3i(entry.getKey().m_142538_().m_123341_(), entry.getKey().m_142538_().m_123342_(), entry.getKey().m_142538_().m_123343_()), 1.5)) {
                if (!entry.getKey().f_19853_.f_46443_) {
                    if (this.controller.getEnergyStorage().getEnergyStored() >= PortalityConfig.TELEPORT_ENERGY_AMOUNT) {
                        ServerLevel tpWorld = entry.getKey().f_19853_.m_142572_().m_129880_(entry.getValue().data.getDimension());
                        Direction tpFacing = Direction.NORTH;
                        if (this.controller.getLinkData().isToken()) {
                            tpFacing = Direction.m_122402_((String)this.controller.getTeleportationTokens().get(this.controller.getLinkData().getName()).m_128461_("Direction"));
                        } else if (tpWorld.m_8055_(entry.getValue().data.getPos()).m_60734_() instanceof ControllerBlock) {
                            tpFacing = (Direction)tpWorld.m_8055_(entry.getValue().data.getPos()).m_61143_((Property)ControllerBlock.FACING_HORIZONTAL);
                        }
                        BlockPos pos = entry.getValue().data.getPos().m_5484_(tpFacing, 2);
                        Entity entity = TeleportationUtils.teleportEntity((Entity)entry.getKey(), entry.getValue().data.getDimension(), (double)((double)pos.m_123341_() + 0.5), (double)(pos.m_123342_() + 2), (double)((double)pos.m_123343_() + 0.5), (float)tpFacing.m_122435_(), (float)0.0f);
                        this.entitesTeleported.put(entity, new TeleportedEntityData(entry.getValue().data));
                        this.controller.getEnergyStorage().extractEnergy(PortalityConfig.TELEPORT_ENERGY_AMOUNT, false);
                        if (entry.getKey() instanceof ServerPlayer) {
                            Portality.NETWORK.get().sendTo((Object)new PortalTeleportMessage(tpFacing.m_122411_(), this.controller.getLength()), ((ServerPlayer)entry.getKey()).f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
                        }
                        if (this.controller.teleportedEntity()) {
                            return;
                        }
                    } else if (entry.getKey() instanceof LivingEntity && PortalityConfig.HURT_PLAYERS) {
                        ((LivingEntity)entry.getKey()).m_7292_(new MobEffectInstance(MobEffects.f_19615_, 100, 1));
                    }
                }
                entityRemove.add(entry.getKey());
                continue;
            }
            entry.getKey().m_20334_(destination.f_82479_, destination.f_82480_, destination.f_82481_);
        }
        for (Entity entity : entityRemove) {
            this.entityTimeToTeleport.remove(entity);
        }
        entityRemove.clear();
        for (Map.Entry<Object, Object> entry : this.entitesTeleported.entrySet()) {
            ++((TeleportedEntityData)entry.getValue()).ticks;
            if (((TeleportedEntityData)entry.getValue()).ticks > 2 && !((TeleportedEntityData)entry.getValue()).moved) {
                if (((Entity)entry.getKey()).f_19853_.f_46443_) {
                    ((Entity)entry.getKey()).f_19853_.m_45976_(ServerPlayer.class, new AABB((double)((Entity)entry.getKey()).m_142538_().m_123341_(), (double)((Entity)entry.getKey()).m_142538_().m_123342_(), (double)((Entity)entry.getKey()).m_142538_().m_123343_(), (double)((Entity)entry.getKey()).m_142538_().m_123341_(), (double)((Entity)entry.getKey()).m_142538_().m_123342_(), (double)((Entity)entry.getKey()).m_142538_().m_123343_()).m_82400_(16.0)).forEach(entityPlayer -> entityPlayer.f_8906_.m_141995_((Packet)new ClientboundCustomSoundPacket(((SoundEvent)PortalitySoundHandler.PORTAL_TP.get()).getRegistryName(), SoundSource.BLOCKS, new Vec3((double)((Entity)entry.getKey()).m_142538_().m_123341_(), (double)((Entity)entry.getKey()).m_142538_().m_123342_(), (double)((Entity)entry.getKey()).m_142538_().m_123343_()), 0.5f, 1.0f)));
                }
                ((TeleportedEntityData)entry.getValue()).moved = true;
                Level tpWorld = ((Entity)entry.getKey()).f_19853_;
                Direction tpFacing = Direction.NORTH;
                if (this.controller.getLinkData().isToken()) {
                    tpFacing = Direction.m_122402_((String)this.controller.getTeleportationTokens().get(this.controller.getLinkData().getName()).m_128461_("Direction"));
                } else if (tpWorld.m_8055_(((TeleportedEntityData)entry.getValue()).data.getPos()).m_60734_() instanceof ControllerBlock) {
                    tpFacing = (Direction)tpWorld.m_8055_(((TeleportedEntityData)entry.getValue()).data.getPos()).m_61143_((Property)ControllerBlock.FACING_HORIZONTAL);
                }
                Vec3 vec3d = new Vec3((double)tpFacing.m_122436_().m_123341_(), (double)tpFacing.m_122436_().m_123342_(), (double)tpFacing.m_122436_().m_123343_()).m_82490_((double)(2 * this.controller.getLength()) / (double)PortalityConfig.MAX_PORTAL_LENGTH);
                ((Entity)entry.getKey()).m_20334_(vec3d.f_82479_, vec3d.f_82480_, vec3d.f_82481_);
                ((Entity)entry.getKey()).m_5616_(tpFacing.m_122435_());
            }
            if (((TeleportedEntityData)entry.getValue()).ticks <= 40) continue;
            entityRemove.add((Entity)entry.getKey());
        }
        for (Entity entity : entityRemove) {
            this.entitesTeleported.remove(entity);
        }
    }

    private class TeleportData {
        private PortalLinkData data;
        private double time;

        public TeleportData(PortalLinkData data) {
            this.data = data;
            this.time = 0.0;
        }
    }

    private class TeleportedEntityData {
        private int ticks;
        private boolean moved;
        private PortalLinkData data;

        public TeleportedEntityData(PortalLinkData data) {
            this.data = data;
            this.ticks = 0;
            this.moved = false;
        }
    }
}

