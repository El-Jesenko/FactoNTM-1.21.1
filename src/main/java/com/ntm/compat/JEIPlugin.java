package com.ntm.compat;

import com.ntm.init.BlockInit;
import com.ntm.init.RecipeInit;
import com.ntm.recipe.AlloyFurnaceRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath("ntm", "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new AlloyFurnaceRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if (Minecraft.getInstance().level == null) return;
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        // Lädt alle generierten JSON-Rezepte in JEI
        List<AlloyFurnaceRecipe> recipes = recipeManager.getAllRecipesFor(RecipeInit.ALLOY_TYPE.get())
                .stream().map(RecipeHolder::value).toList();

        registration.addRecipes(AlloyFurnaceRecipeCategory.TYPE, recipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        // Verknüpft deinen Block mit der Rezept-Kategorie
        registration.addRecipeCatalyst(new ItemStack(BlockInit.ALLOY_FURNACE.get()), AlloyFurnaceRecipeCategory.TYPE);
    }
}