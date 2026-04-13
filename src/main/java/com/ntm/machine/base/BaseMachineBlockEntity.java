package com.ntm.machine.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public abstract class BaseMachineBlockEntity extends BlockEntity {
    protected final ItemStackHandler inventory;

    protected int progress = 0;
    protected int maxProgress = 100;
    protected int fuelTime = 0;
    protected int maxFuelTime = 0;
    protected boolean isWorking = false;

    protected BaseMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, int inventorySlots) {
        super(type, pos, blockState);
        this.inventory = new ItemStackHandler(inventorySlots) {
            @Override
            protected void onContentsChanged(int slot) {
                BaseMachineBlockEntity.this.onInventoryChanged(slot);
                setChanged();
            }
        };
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> progress;
                case 1 -> maxProgress;
                case 2 -> fuelTime;
                case 3 -> getFuelBarMax();
                case 4 -> isWorking ? 1 : 0;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> progress = value;
                case 1 -> maxProgress = value;
                case 2 -> fuelTime = value;
                case 4 -> isWorking = value > 0;
                default -> {
                }
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    };

    public final void tickServer() {
        if (level == null || level.isClientSide()) {
            return;
        }

        boolean changed = false;
        changed |= absorbFuel();

        boolean canWork = canProcess();
        if (canWork && fuelTime > 0) {
            if (!isWorking) {
                isWorking = true;
                changed = true;
            }

            progress++;
            fuelTime--;
            if (progress >= maxProgress) {
                processRecipe();
                progress = 0;
            }
            changed = true;
        } else {
            if (isWorking) {
                isWorking = false;
                changed = true;
            }
            if (progress != 0) {
                progress = 0;
                changed = true;
            }
        }

        changed |= updateLitBlockState(isWorking);
        if (changed) {
            setChanged();
        }
    }

    protected boolean updateLitBlockState(boolean lit) {
        if (level == null || level.isClientSide()) {
            return false;
        }
        BlockState current = getBlockState();
        if (!current.hasProperty(net.minecraft.world.level.block.state.properties.BlockStateProperties.LIT)) {
            return false;
        }
        if (current.getValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.LIT) == lit) {
            return false;
        }
        level.setBlock(getBlockPos(), current.setValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.LIT, lit), 3);
        return true;
    }

    protected abstract boolean absorbFuel();

    protected abstract boolean canProcess();

    protected abstract void processRecipe();

    protected abstract int getFuelBarMax();

    protected void onInventoryChanged(int slot) {
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.putInt("progress", progress);
        tag.putInt("maxProgress", maxProgress);
        tag.putInt("fuelTime", fuelTime);
        tag.putInt("maxFuelTime", maxFuelTime);
        tag.putBoolean("isWorking", isWorking);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("progress");
        maxProgress = tag.getInt("maxProgress");
        fuelTime = tag.getInt("fuelTime");
        maxFuelTime = tag.getInt("maxFuelTime");
        isWorking = tag.getBoolean("isWorking");
    }
}
