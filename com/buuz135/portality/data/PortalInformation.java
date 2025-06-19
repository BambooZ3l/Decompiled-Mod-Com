/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Registry
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package com.buuz135.portality.data;

import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PortalInformation {
    private UUID id;
    private UUID owner;
    private String name;
    private boolean isActive;
    private boolean isPrivate;
    private ResourceKey<Level> dimension;
    private BlockPos location;
    private ItemStack display;
    private boolean interdimensional;
    private boolean isToken;

    public PortalInformation(UUID id, UUID owner, boolean isActive, boolean isPrivate, ResourceKey<Level> dimension, BlockPos location, String name, ItemStack display, boolean interdimensional) {
        this.id = id;
        this.owner = owner;
        this.isActive = isActive;
        this.isPrivate = isPrivate;
        this.dimension = dimension;
        this.location = location;
        this.name = name;
        this.display = display;
        this.interdimensional = interdimensional;
        this.isToken = false;
    }

    public static PortalInformation readFromNBT(CompoundTag info) {
        return new PortalInformation(info.m_128342_("ID"), info.m_128342_("Owner"), info.m_128471_("Active"), info.m_128471_("Private"), (ResourceKey<Level>)ResourceKey.m_135785_((ResourceKey)Registry.f_122819_, (ResourceLocation)new ResourceLocation(info.m_128461_("Dimension"))), BlockPos.m_122022_((long)info.m_128454_("Position")), info.m_128461_("Name"), ItemStack.m_41712_((CompoundTag)info.m_128469_("Display")), info.m_128471_("Interdimensional")).setToken(info.m_128471_("Token"));
    }

    public UUID getId() {
        return this.id;
    }

    public UUID getOwner() {
        return this.owner;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public boolean isPrivate() {
        return this.isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        this.isPrivate = aPrivate;
    }

    public ResourceKey<Level> getDimension() {
        return this.dimension;
    }

    public BlockPos getLocation() {
        return this.location;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemStack getDisplay() {
        return this.display;
    }

    public void setDisplay(ItemStack display) {
        this.display = display;
    }

    public boolean isInterdimensional() {
        return this.interdimensional;
    }

    public void setInterdimensional(boolean interdimensional) {
        this.interdimensional = interdimensional;
    }

    public boolean isToken() {
        return this.isToken;
    }

    public PortalInformation setToken(boolean token) {
        this.isToken = token;
        return this;
    }

    public CompoundTag writetoNBT() {
        CompoundTag infoTag = new CompoundTag();
        infoTag.m_128362_("ID", this.getId());
        infoTag.m_128362_("Owner", this.getOwner());
        infoTag.m_128379_("Active", this.isActive());
        infoTag.m_128379_("Private", this.isPrivate());
        infoTag.m_128359_("Dimension", this.getDimension().m_135782_().toString());
        infoTag.m_128356_("Position", this.getLocation().m_121878_());
        infoTag.m_128359_("Name", this.getName());
        infoTag.m_128365_("Display", (Tag)this.display.serializeNBT());
        infoTag.m_128379_("Interdimensional", this.interdimensional);
        infoTag.m_128379_("Token", this.isToken());
        return infoTag;
    }

    public String toString() {
        return "PortalInformation{id=" + this.id + ", owner=" + this.owner + ", isActive=" + this.isActive + ", isPrivate=" + this.isPrivate + ", dimension=" + this.dimension + ", location=" + this.location + "}";
    }
}

