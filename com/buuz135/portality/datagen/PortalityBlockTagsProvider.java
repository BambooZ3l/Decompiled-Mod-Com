/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.data.DataGenerator
 *  net.minecraft.data.tags.BlockTagsProvider
 *  net.minecraft.tags.BlockTags
 *  net.minecraftforge.common.data.ExistingFileHelper
 *  net.minecraftforge.registries.ForgeRegistries
 *  org.jetbrains.annotations.Nullable
 */
package com.buuz135.portality.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class PortalityBlockTagsProvider
extends BlockTagsProvider {
    public PortalityBlockTagsProvider(DataGenerator p_126530_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_126530_, modId, existingFileHelper);
    }

    protected void m_6577_() {
        ForgeRegistries.BLOCKS.getValues().stream().filter(block -> block.getRegistryName().m_135827_().equals("portality")).forEach(block -> this.m_206424_(BlockTags.f_144282_).m_126582_(block));
    }
}

