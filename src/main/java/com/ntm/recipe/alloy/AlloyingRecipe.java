package com.ntm.recipe.alloy;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ntm.recipe.ModRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record AlloyingRecipe(Ingredient firstInput, Ingredient secondInput, ItemStack output, int ticks) implements Recipe<AlloyingRecipe.Input> {
    @Override
    public boolean matches(Input input, Level level) {
        return firstInput.test(input.getItem(0)) && secondInput.test(input.getItem(1));
    }

    @Override
    public ItemStack assemble(Input input, HolderLookup.Provider registries) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, firstInput, secondInput);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ALLOYING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.ALLOYING_TYPE.get();
    }

    public record Input(ItemStack first, ItemStack second) implements RecipeInput {
        @Override
        public ItemStack getItem(int slotIndex) {
            return switch (slotIndex) {
                case 0 -> first;
                case 1 -> second;
                default -> throw new IllegalArgumentException("No item for slot " + slotIndex);
            };
        }

        @Override
        public int size() {
            return 2;
        }
    }

    public static class Serializer implements RecipeSerializer<AlloyingRecipe> {
        public static final MapCodec<AlloyingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("first").forGetter(AlloyingRecipe::firstInput),
                Ingredient.CODEC_NONEMPTY.fieldOf("second").forGetter(AlloyingRecipe::secondInput),
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(AlloyingRecipe::output),
                Codec.INT.optionalFieldOf("ticks", 100).forGetter(AlloyingRecipe::ticks)
        ).apply(instance, AlloyingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, AlloyingRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, AlloyingRecipe::firstInput,
                Ingredient.CONTENTS_STREAM_CODEC, AlloyingRecipe::secondInput,
                ItemStack.STREAM_CODEC, AlloyingRecipe::output,
                ByteBufCodecs.VAR_INT, AlloyingRecipe::ticks,
                AlloyingRecipe::new
        );

        @Override
        public MapCodec<AlloyingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AlloyingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
