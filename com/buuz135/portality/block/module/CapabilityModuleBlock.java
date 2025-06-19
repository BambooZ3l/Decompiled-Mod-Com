/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.block.RotatableBlock$RotationType
 *  javax.annotation.Nonnull
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.common.capabilities.Capability
 */
package com.buuz135.portality.block.module;

import com.buuz135.portality.Portality;
import com.buuz135.portality.block.FrameBlock;
import com.buuz135.portality.block.module.IPortalModule;
import com.buuz135.portality.tile.ControllerTile;
import com.buuz135.portality.tile.FrameTile;
import com.hrznstudio.titanium.block.RotatableBlock;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.common.capabilities.Capability;

public abstract class CapabilityModuleBlock<T, S extends FrameTile<S>>
extends FrameBlock<S>
implements IPortalModule {
    public static BooleanProperty INPUT = BooleanProperty.m_61465_((String)"input");

    public CapabilityModuleBlock(String name, Class<S> tileClass) {
        super(name, tileClass);
        this.m_49959_((BlockState)this.m_49966_().m_61124_((Property)INPUT, (Comparable)Boolean.valueOf(true)));
        this.setItemGroup(Portality.TAB);
    }

    @Override
    public void work(ControllerTile controller, BlockPos blockPos) {
        if (controller.getLinkData() == null) {
            return;
        }
        BlockEntity other = controller.m_58904_().m_142572_().m_129880_(controller.getLinkData().getDimension()).m_7702_(controller.getLinkData().getPos());
        if (other instanceof ControllerTile && this.isInput(controller.m_58904_().m_8055_(blockPos))) {
            ControllerTile otherController = (ControllerTile)other;
            this.internalWork(controller.m_58904_(), blockPos, other.m_58904_(), otherController.getModules().stream().filter(pos -> otherController.m_58904_().m_8055_(pos).m_60734_() instanceof CapabilityModuleBlock && !((CapabilityModuleBlock)otherController.m_58904_().m_8055_(pos).m_60734_()).isInput(otherController.m_58904_().m_8055_(pos)) && ((CapabilityModuleBlock)otherController.m_58904_().m_8055_(pos).m_60734_()).getCapability().equals(this.getCapability())).collect(Collectors.toList()));
        }
    }

    @Override
    @Nonnull
    public RotatableBlock.RotationType getRotationType() {
        return RotatableBlock.RotationType.FOUR_WAY;
    }

    public abstract Capability<T> getCapability();

    public boolean isInput(BlockState state) {
        return (Boolean)state.m_61143_((Property)INPUT);
    }

    abstract void internalWork(Level var1, BlockPos var2, Level var3, List<BlockPos> var4);

    @Override
    public boolean allowsInterdimensionalTravel() {
        return false;
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        super.m_7926_(builder);
        builder.m_61104_(new Property[]{INPUT});
    }

    public ItemStack m_7397_(BlockGetter p_49823_, BlockPos p_49824_, BlockState p_49825_) {
        return new ItemStack((ItemLike)this, 1);
    }
}

