package com.ntm.generator;

import com.ntm.init.BlockInit;
import com.ntm.init.ItemInit;
import com.ntm.main.NTM;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        // 1. Definiere alle Items, die zu einem Ingot geschmolzen werden können
        Ingredient titaniumSmeltables = Ingredient.of(
                ItemInit.RAW_TITANIUM.get(),
                BlockInit.ORE_TITANIUM.get(),
                BlockInit.ORE_TITANIUM_DEEPSLATE.get()
        );

        // 2. Normaler Ofen für ALLE diese Items
        SimpleCookingRecipeBuilder.smelting(
                        titaniumSmeltables,
                        RecipeCategory.MISC,
                        ItemInit.INGOT_TITANIUM.get(),
                        0.7f,
                        200
                ).unlockedBy("has_titanium", has(ItemInit.RAW_TITANIUM.get()))
                .save(output, NTM.MODID + ":smelting_titanium_ingot");

        // 3. Schmelzofen für ALLE diese Items
        SimpleCookingRecipeBuilder.blasting(
                        titaniumSmeltables,
                        RecipeCategory.MISC,
                        ItemInit.INGOT_TITANIUM.get(),
                        0.7f,
                        100
                ).unlockedBy("has_titanium", has(ItemInit.RAW_TITANIUM.get()))
                .save(output, NTM.MODID + ":blasting_titanium_ingot");

        // 9 Ingots -> 1 Block
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockInit.BLOCK_TITANIUM.get())
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I', ItemInit.INGOT_TITANIUM.get())
                .unlockedBy("has_ingot", has(ItemInit.INGOT_TITANIUM.get()))
                .save(output, NTM.MODID + ":titanium_block_from_ingots");

// 1 Block -> 9 Ingots
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.INGOT_TITANIUM.get(), 9)
                .requires(BlockInit.BLOCK_TITANIUM.get())
                .unlockedBy("has_block", has(BlockInit.BLOCK_TITANIUM.get()))
                .save(output, NTM.MODID + ":titanium_ingots_from_block");

// 9 Nuggets -> 1 Ingot
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemInit.INGOT_TITANIUM.get())
                .pattern("NNN")
                .pattern("NNN")
                .pattern("NNN")
                .define('N', ItemInit.NUGGET_TITANIUM.get())
                .unlockedBy("has_nugget", has(ItemInit.NUGGET_TITANIUM.get()))
                .save(output, NTM.MODID + ":titanium_ingot_from_nuggets");

// 1 Ingot -> 9 Nuggets
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.NUGGET_TITANIUM.get(), 9)
                .requires(ItemInit.INGOT_TITANIUM.get())
                .unlockedBy("has_ingot", has(ItemInit.INGOT_TITANIUM.get()))
                .save(output, NTM.MODID + ":titanium_nuggets_from_ingot");

    }
}
