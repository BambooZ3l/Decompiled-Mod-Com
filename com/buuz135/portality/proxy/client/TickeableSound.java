/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.sounds.AbstractSoundInstance
 *  net.minecraft.client.resources.sounds.TickableSoundInstance
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package com.buuz135.portality.proxy.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class TickeableSound
extends AbstractSoundInstance
implements TickableSoundInstance {
    private boolean done;
    private Level world;

    public TickeableSound(Level world, BlockPos pos, SoundEvent soundIn) {
        super(soundIn, SoundSource.BLOCKS);
        this.world = world;
        this.f_119575_ = pos.m_123341_();
        this.f_119576_ = pos.m_123342_();
        this.f_119577_ = pos.m_123343_();
        this.f_119578_ = true;
        this.done = false;
        this.f_119573_ = 0.35f;
        this.f_119574_ = 0.0f;
        this.f_119582_ = false;
    }

    public boolean m_7801_() {
        return this.done;
    }

    public void setDone() {
        this.done = true;
    }

    public void increase() {
        if (this.f_119574_ < 1.0f) {
            this.f_119574_ = (float)((double)this.f_119574_ + 0.03);
        }
    }

    public void decrease() {
        if (this.f_119574_ > 0.0f) {
            this.f_119574_ = (float)((double)this.f_119574_ - 0.03);
        }
    }

    public void m_7788_() {
        double distance;
        if (this.world.m_7702_(new BlockPos(this.f_119575_, this.f_119576_, this.f_119577_)) == null) {
            this.setDone();
        }
        if ((distance = (double)Minecraft.m_91087_().f_91074_.m_142538_().m_123333_((Vec3i)new BlockPos(this.f_119575_, this.f_119576_, this.f_119577_))) > 16.0) {
            this.f_119573_ = 0.0f;
        } else {
            if (distance == 0.0) {
                distance = 1.0;
            }
            this.f_119573_ = (float)(0.35 * (1.0 / distance));
        }
    }
}

