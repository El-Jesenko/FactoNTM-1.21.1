package com.ntm.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ntm.init.RecipeInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record AlloyFurnaceRecipe(Ingredient input1, Ingredient input2, ItemStack output) implements Recipe<AlloyRecipeInput> {

    // Liest/Schreibt die JSON-Dateien
    public static final MapCodec<AlloyFurnaceRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC_NONEMPTY.fieldOf("input1").forGetter(AlloyFurnaceRecipe::input1),
            Ingredient.CODEC_NONEMPTY.fieldOf("input2").forGetter(AlloyFurnaceRecipe::input2),
            ItemStack.CODEC.fieldOf("output").forGetter(AlloyFurnaceRecipe::output)
    ).apply(inst, AlloyFurnaceRecipe::new));

    // Überträgt das Rezept an den Client
    public static final StreamCodec<RegistryFriendlyByteBuf, AlloyFurnaceRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, AlloyFurnaceRecipe::input1,
            Ingredient.CONTENTS_STREAM_CODEC, AlloyFurnaceRecipe::input2,
            ItemStack.STREAM_CODEC, AlloyFurnaceRecipe::output,
            AlloyFurnaceRecipe::new
    );

    @Override
    public boolean matches(AlloyRecipeInput input, Level level) {
        // Prüft beide Slots, Reihenfolge egal
        return (this.input1.test(input.input1()) && this.input2.test(input.input2())) ||
                (this.input1.test(input.input2()) && this.input2.test(input.input1()));
    }

    @Override
    public ItemStack assemble(AlloyRecipeInput input, HolderLookup.Provider registries) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) { return true; }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) { return output; }

    @Override
    public RecipeSerializer<?> getSerializer() { return RecipeInit.ALLOY_SERIALIZER.get(); }

    @Override
    public RecipeType<?> getType() { return RecipeInit.ALLOY_TYPE.get(); }
}