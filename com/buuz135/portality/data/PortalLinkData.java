/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Registry
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.Level
 */
package com.buuz135.portality.data;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class PortalLinkData {
    private ResourceKey<Level> dimension;
    private BlockPos pos;
    private boolean caller;
    private String name;
    private boolean token;

    public PortalLinkData(ResourceKey<Level> dimension, BlockPos pos, boolean caller, String name, boolean token) {
        this.dimension = dimension;
        this.pos = pos;
        this.caller = caller;
        this.name = name;
        this.token = token;
    }

    public static PortalLinkData readFromNBT(CompoundTag compound) {
        return new PortalLinkData((ResourceKey<Level>)ResourceKey.m_135785_((ResourceKey)Registry.f_122819_, (ResourceLocation)new ResourceLocation(compound.m_128461_("Dimension"))), BlockPos.m_122022_((long)compound.m_128454_("Position")), compound.m_128471_("Caller"), compound.m_128461_("Name"), compound.m_128471_("Token"));
    }

    public ResourceKey<Level> getDimension() {
        return this.dimension;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public boolean isCaller() {
        return this.caller;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isToken() {
        return this.token;
    }

    public CompoundTag writeToNBT() {
        CompoundTag tagCompound = new CompoundTag();
        tagCompound.m_128359_("Dimension", this.dimension.m_135782_().toString());
        tagCompound.m_128356_("Position", this.pos.m_121878_());
        tagCompound.m_128379_("Caller", this.caller);
        tagCompound.m_128359_("Name", this.name);
        tagCompound.m_128379_("Token", this.token);
        return tagCompound;
    }

    public static enum PortalCallType {
        NORMAL,
        SINGLE,
        FORCE;

    }
}

