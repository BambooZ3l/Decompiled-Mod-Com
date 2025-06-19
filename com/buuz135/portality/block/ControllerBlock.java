/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.block.RotatableBlock
 *  com.hrznstudio.titanium.block.RotatableBlock$RotationType
 *  javax.annotation.Nonnull
 *  javax.annotation.Nullable
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.TranslatableComponent
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.Explosion
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType$BlockEntitySupplier
 *  net.minecraft.world.level.block.state.BlockBehaviour
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraftforge.registries.RegistryObject
 */
package com.buuz135.portality.block;

import com.buuz135.portality.Portality;
import com.buuz135.portality.data.PortalDataManager;
import com.buuz135.portality.data.PortalInformation;
import com.buuz135.portality.proxy.CommonProxy;
import com.buuz135.portality.tile.ControllerTile;
import com.hrznstudio.titanium.block.RotatableBlock;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.RegistryObject;

public class ControllerBlock
extends RotatableBlock<ControllerTile> {
    public ControllerBlock() {
        super("controller", BlockBehaviour.Properties.m_60926_((BlockBehaviour)Blocks.f_50075_), ControllerTile.class);
        this.setItemGroup(Portality.TAB);
    }

    public void m_6402_(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        PortalInformation information = new PortalInformation(UUID.randomUUID(), placer.m_142081_(), false, false, (ResourceKey<Level>)worldIn.m_46472_(), pos, "X: " + pos.m_123341_() + " Y: " + pos.m_123342_() + " Z: " + pos.m_123343_(), new ItemStack((ItemLike)((RegistryObject)CommonProxy.BLOCK_FRAME.getLeft()).get()), false);
        PortalDataManager.addInformation(worldIn, information);
        super.m_6402_(worldIn, pos, state, placer, stack);
    }

    public void m_6786_(LevelAccessor worldIn, BlockPos pos, BlockState state) {
        super.m_6786_(worldIn, pos, state);
        PortalDataManager.removeInformation(worldIn, pos);
    }

    public void m_7592_(Level worldIn, BlockPos pos, Explosion explosionIn) {
        super.m_7592_(worldIn, pos, explosionIn);
        PortalDataManager.removeInformation((LevelAccessor)worldIn, pos);
    }

    @Nullable
    public BlockState m_5573_(BlockPlaceContext context) {
        return (BlockState)this.m_49966_().m_61124_((Property)FACING_HORIZONTAL, (Comparable)context.m_8125_().m_122424_());
    }

    @Nonnull
    public RotatableBlock.RotationType getRotationType() {
        return RotatableBlock.RotationType.FOUR_WAY;
    }

    public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player playerIn, InteractionHand hand, BlockHitResult ray) {
        BlockEntity tile = worldIn.m_7702_(pos);
        if (tile instanceof ControllerTile) {
            ControllerTile controller = (ControllerTile)tile;
            if (!worldIn.m_5776_()) {
                if (!controller.isFormed()) {
                    playerIn.m_5661_((Component)new TranslatableComponent("portality.controller.error.size").m_130940_(ChatFormatting.RED), true);
                    return InteractionResult.SUCCESS;
                }
                if (controller.isPrivate() && !controller.getOwner().equals(playerIn.m_142081_())) {
                    playerIn.m_5661_((Component)new TranslatableComponent("portality.controller.error.privacy").m_130940_(ChatFormatting.RED), true);
                    return InteractionResult.SUCCESS;
                }
            } else if (controller.isFormed()) {
                if (controller.isPrivate() && !controller.getOwner().equals(playerIn.m_142081_())) {
                    return InteractionResult.SUCCESS;
                }
                Minecraft.m_91087_().m_18689_(() -> ControllerTile.OpenGui.open(0, (ControllerTile)tile));
                return InteractionResult.SUCCESS;
            }
        }
        return super.m_6227_(state, worldIn, pos, playerIn, hand, ray);
    }

    public void m_6810_(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        BlockEntity entity = worldIn.m_7702_(pos);
        if (entity instanceof ControllerTile) {
            ((ControllerTile)entity).breakController();
        }
        super.m_6810_(state, worldIn, pos, newState, isMoving);
    }

    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return ControllerTile::new;
    }
}

