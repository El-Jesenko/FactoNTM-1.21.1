package com.ntm.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record AlloyRecipeInput(ItemStack input1, ItemStack input2) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        return index == 0 ? input1 : input2;
    }

    @Override
    public int size() {
        return 2;
    }
}