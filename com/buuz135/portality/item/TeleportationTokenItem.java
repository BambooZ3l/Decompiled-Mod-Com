/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.item.BasicItem
 *  com.hrznstudio.titanium.item.BasicItem$Key
 *  javax.annotation.Nullable
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.TextComponent
 *  net.minecraft.network.chat.TranslatableComponent
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.LevelReader
 *  org.apache.commons.lang3.text.WordUtils
 */
package com.buuz135.portality.item;

import com.buuz135.portality.Portality;
import com.buuz135.portality.tile.ControllerTile;
import com.hrznstudio.titanium.item.BasicItem;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.LevelReader;
import org.apache.commons.lang3.text.WordUtils;

public class TeleportationTokenItem
extends BasicItem {
    public TeleportationTokenItem() {
        super(new Item.Properties().m_41487_(1).m_41491_(Portality.TAB));
    }

    public InteractionResult m_6225_(UseOnContext context) {
        if (context.m_43725_().m_7702_(context.m_8083_()) instanceof ControllerTile) {
            return InteractionResult.PASS;
        }
        CompoundTag compoundNBT = context.m_43722_().m_41784_();
        compoundNBT.m_128359_("Dimension", context.m_43725_().m_46472_().m_135782_().toString());
        compoundNBT.m_128405_("X", context.m_8083_().m_123341_());
        compoundNBT.m_128405_("Y", context.m_8083_().m_123342_());
        compoundNBT.m_128405_("Z", context.m_8083_().m_123343_());
        compoundNBT.m_128359_("Direction", context.m_8125_().name());
        return InteractionResult.SUCCESS;
    }

    public boolean hasTooltipDetails(@Nullable BasicItem.Key key) {
        return key == null;
    }

    public void addTooltipDetails(@Nullable BasicItem.Key key, ItemStack stack, List<Component> tooltip, boolean advanced) {
        super.addTooltipDetails(key, stack, tooltip, advanced);
        if (key == null && stack.m_41782_()) {
            String dimension = stack.m_41784_().m_128461_("Dimension");
            if (dimension.contains(":")) {
                dimension = dimension.split(":")[1];
            }
            tooltip.add((Component)new TranslatableComponent("portality.display.dimension").m_7220_((Component)new TextComponent(WordUtils.capitalize((String)dimension))).m_130940_(ChatFormatting.GRAY));
            tooltip.add((Component)new TranslatableComponent("portality.display.position").m_7220_((Component)new TextComponent(stack.m_41784_().m_128451_("X") + ", " + stack.m_41784_().m_128451_("Y") + ", " + stack.m_41784_().m_128451_("Z"))).m_130940_(ChatFormatting.GRAY));
            tooltip.add((Component)new TranslatableComponent("portality.display.direction").m_7220_((Component)new TextComponent(WordUtils.capitalize((String)stack.m_41784_().m_128461_("Direction").toLowerCase(Locale.ROOT)))).m_130940_(ChatFormatting.GRAY));
        }
    }

    public boolean m_5812_(ItemStack stack) {
        return stack.m_41782_();
    }

    public boolean doesSneakBypassUse(ItemStack stack, LevelReader world, BlockPos pos, Player player) {
        return world.m_7702_(pos) instanceof ControllerTile;
    }
}

