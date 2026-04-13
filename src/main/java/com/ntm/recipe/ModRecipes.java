package com.ntm.recipe;

import com.ntm.content.registry.ModRegistries;
import com.ntm.main.NTM;
import com.ntm.recipe.alloy.AlloyingRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class ModRecipes {
    public static final DeferredHolder<RecipeType<?>, RecipeType<AlloyingRecipe>> ALLOYING_TYPE =
            ModRegistries.RECIPE_TYPES.register("alloying", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(NTM.MODID, "alloying")));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AlloyingRecipe>> ALLOYING_SERIALIZER =
            ModRegistries.RECIPE_SERIALIZERS.register("alloying", AlloyingRecipe.Serializer::new);

    private ModRecipes() {
    }
}
