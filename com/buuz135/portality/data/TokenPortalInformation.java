/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 */
package com.buuz135.portality.data;

import com.buuz135.portality.data.PortalInformation;
import com.buuz135.portality.proxy.CommonProxy;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class TokenPortalInformation
extends PortalInformation {
    public TokenPortalInformation(UUID owner, ResourceKey<Level> dimension, BlockPos location, String name) {
        super(UUID.randomUUID(), owner, false, false, dimension, location, name, new ItemStack((ItemLike)CommonProxy.TELEPORTATION_TOKEN_ITEM.get()), true);
        this.setToken(true);
    }
}

