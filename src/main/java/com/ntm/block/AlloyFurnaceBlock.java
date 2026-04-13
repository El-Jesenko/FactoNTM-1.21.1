package com.ntm.block;

import com.ntm.block.entity.AlloyFurnaceBlockEntity;
import com.ntm.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import com.ntm.block.entity.AlloyFurnaceBlockEntity;

public class AlloyFurnaceBlock extends Block implements EntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public AlloyFurnaceBlock(Properties properties) {
        super(properties);
        // Standard-Zustand: Zeigt nach Norden, ist ausgeschaltet
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false));
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        // Rotiert den Block so, dass die Front zum Spieler zeigt
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AlloyFurnaceBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof AlloyFurnaceBlockEntity furnaceEntity) {
                // Wichtig: Auf ServerPlayer prüfen und casten
                if (player instanceof ServerPlayer serverPlayer) {
                    serverPlayer.openMenu((MenuProvider) furnaceEntity, pos);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) return null;

        // Wir prüfen, ob der Typ übereinstimmt, und casten die Entity direkt
        return type == ModBlockEntities.ALLOY_FURNACE_BE.get() ?
                (lvl, pos, st, blockEntity) -> ((AlloyFurnaceBlockEntity) blockEntity).tick() : null;
    }

    // Droppt die Items, wenn der Block abgebaut wird
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof AlloyFurnaceBlockEntity furnace) {
                for (int i = 0; i < furnace.inventory.getSlots(); i++) {
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), furnace.inventory.getStackInSlot(i));
                }
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }
}