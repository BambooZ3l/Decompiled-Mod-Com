/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntityType$BlockEntitySupplier
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 */
package com.buuz135.portality.block;

import com.buuz135.portality.Portality;
import com.buuz135.portality.block.FrameBlock;
import com.buuz135.portality.block.module.IPortalModule;
import com.buuz135.portality.tile.ControllerTile;
import com.buuz135.portality.tile.InterdimensionalFrameTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class InterdimensionalModuleBlock
extends FrameBlock<InterdimensionalFrameTile>
implements IPortalModule {
    public InterdimensionalModuleBlock() {
        super("module_interdimensional", InterdimensionalFrameTile.class);
        this.setItemGroup(Portality.TAB);
    }

    @Override
    public void work(ControllerTile controller, BlockPos pos) {
    }

    @Override
    public boolean allowsInterdimensionalTravel() {
        return true;
    }

    public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray) {
        return InteractionResult.FAIL;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return InterdimensionalFrameTile::new;
    }
}

