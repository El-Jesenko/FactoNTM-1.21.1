package com.ntm.block;

import com.ntm.block.entity.AlloyFurnaceBlockEntity;
import com.ntm.init.ModBlockEntities;
import com.ntm.machine.base.BaseMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class AlloyFurnaceBlock extends BaseMachineBlock<AlloyFurnaceBlockEntity> {
    public AlloyFurnaceBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AlloyFurnaceBlockEntity(pos, state);
    }

    @Override
    protected BlockEntityType<AlloyFurnaceBlockEntity> getMachineType() {
        return ModBlockEntities.ALLOY_FURNACE_BE.get();
    }
}
