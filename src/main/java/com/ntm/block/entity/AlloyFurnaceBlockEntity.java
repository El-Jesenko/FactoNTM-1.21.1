package com.ntm.block.entity;

import com.ntm.init.ModBlockEntities;
import com.ntm.machine.base.BaseMachineBlockEntity;
import com.ntm.menu.AlloyFurnaceMenu;
import com.ntm.recipe.ModRecipes;
import com.ntm.recipe.alloy.AlloyingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AlloyFurnaceBlockEntity extends BaseMachineBlockEntity implements MenuProvider {
    public static final int SLOT_FUEL = 0;
    public static final int SLOT_INPUT_1 = 1;
    public static final int SLOT_INPUT_2 = 2;
    public static final int SLOT_OUTPUT = 3;
    public static final int MAX_FUEL = 20000;

    private int recipeCacheKey = Integer.MIN_VALUE;
    private Optional<AlloyingRecipe> cachedRecipe = Optional.empty();

    public AlloyFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ALLOY_FURNACE_BE.get(), pos, state, 4);
    }

    @Override
    protected boolean absorbFuel() {
        ItemStack fuelStack = inventory.getStackInSlot(SLOT_FUEL);
        if (fuelStack.isEmpty()) {
            return false;
        }

        int burnTime = fuelStack.getBurnTime(RecipeType.SMELTING);
        if (burnTime <= 0 || fuelTime + burnTime > MAX_FUEL) {
            return false;
        }

        fuelTime += burnTime;
        fuelStack.shrink(1);
        return true;
    }

    @Override
    protected boolean canProcess() {
        AlloyingRecipe recipe = getOrResolveRecipe().orElse(null);
        if (recipe == null) {
            return false;
        }

        ItemStack outputSlot = inventory.getStackInSlot(SLOT_OUTPUT);
        ItemStack result = recipe.output();
        if (outputSlot.isEmpty()) {
            maxProgress = recipe.ticks();
            return true;
        }

        if (!ItemStack.isSameItemSameComponents(outputSlot, result)) {
            return false;
        }

        boolean hasSpace = outputSlot.getCount() + result.getCount() <= outputSlot.getMaxStackSize();
        if (hasSpace) {
            maxProgress = recipe.ticks();
        }
        return hasSpace;
    }

    @Override
    protected void processRecipe() {
        AlloyingRecipe recipe = getOrResolveRecipe().orElse(null);
        if (recipe == null) {
            return;
        }

        inventory.getStackInSlot(SLOT_INPUT_1).shrink(1);
        inventory.getStackInSlot(SLOT_INPUT_2).shrink(1);
        inventory.insertItem(SLOT_OUTPUT, recipe.output().copy(), false);
        invalidateRecipeCache();
    }

    private Optional<AlloyingRecipe> getOrResolveRecipe() {
        if (level == null) {
            return Optional.empty();
        }

        int currentKey = computeInputKey();
        if (currentKey == recipeCacheKey) {
            return cachedRecipe;
        }

        AlloyingRecipe.Input input = new AlloyingRecipe.Input(
                inventory.getStackInSlot(SLOT_INPUT_1),
                inventory.getStackInSlot(SLOT_INPUT_2)
        );
        cachedRecipe = level.getRecipeManager().getRecipeFor(ModRecipes.ALLOYING_TYPE.get(), input, level).map(recipeHolder -> recipeHolder.value());
        recipeCacheKey = currentKey;
        return cachedRecipe;
    }

    private int computeInputKey() {
        ItemStack first = inventory.getStackInSlot(SLOT_INPUT_1);
        ItemStack second = inventory.getStackInSlot(SLOT_INPUT_2);
        int hash = 17;
        hash = 31 * hash + first.hashCode();
        hash = 31 * hash + second.hashCode();
        return hash;
    }

    private void invalidateRecipeCache() {
        recipeCacheKey = Integer.MIN_VALUE;
        cachedRecipe = Optional.empty();
    }

    @Override
    protected void onInventoryChanged(int slot) {
        if (slot == SLOT_INPUT_1 || slot == SLOT_INPUT_2) {
            invalidateRecipeCache();
        }
    }

    @Override
    protected int getFuelBarMax() {
        return MAX_FUEL;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.ntm.alloy_furnace");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new AlloyFurnaceMenu(containerId, playerInventory, this, this.data);
    }
}
