package com.ntm.init;

import com.mojang.serialization.MapCodec;
import com.ntm.recipe.AlloyFurnaceRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RecipeInit {
    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, "ntm");
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, "ntm");

    public static final Supplier<RecipeType<AlloyFurnaceRecipe>> ALLOY_TYPE = TYPES.register("alloying", () -> new RecipeType<>() {});

    public static final Supplier<RecipeSerializer<AlloyFurnaceRecipe>> ALLOY_SERIALIZER = SERIALIZERS.register("alloying", () ->
            new RecipeSerializer<>() {
                @Override
                public MapCodec<AlloyFurnaceRecipe> codec() { return AlloyFurnaceRecipe.CODEC; }
                @Override
                public StreamCodec<RegistryFriendlyByteBuf, AlloyFurnaceRecipe> streamCodec() { return AlloyFurnaceRecipe.STREAM_CODEC; }
            }
    );
}