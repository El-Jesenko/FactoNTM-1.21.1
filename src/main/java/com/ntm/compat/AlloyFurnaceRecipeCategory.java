package com.ntm.compat;

import com.ntm.init.BlockInit;
import com.ntm.recipe.AlloyFurnaceRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class AlloyFurnaceRecipeCategory implements IRecipeCategory<AlloyFurnaceRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath("ntm", "alloying");
    public static final RecipeType<AlloyFurnaceRecipe> TYPE = new RecipeType<>(UID, AlloyFurnaceRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated animatedArrow;
    private final IDrawableAnimated animatedLava; // NEU
    private final IDrawableAnimated animatedFlame; // NEU

    public AlloyFurnaceRecipeCategory(IGuiHelper helper) {
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath("ntm", "textures/gui/alloy_furnace.png");

        // 1. Hintergrund-Ausschnitt (Bleibt gleich: nimmt den Tank links mit rein)
        this.background = helper.createDrawable(texture, 40, 15, 120, 60);

        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockInit.ALLOY_FURNACE.get()));

        // 100 Ticks = 5 Sekunden für eine volle Animation
        int animationDurationInTicks = 100;

        // 2. Animierter Pfeil (Bleibt gleich: Holt sich das Sprite)
        IDrawableStatic staticArrow = helper.createDrawable(texture, 176, 14, 24, 17);
        this.animatedArrow = helper.createAnimatedDrawable(staticArrow, animationDurationInTicks, IDrawableAnimated.StartDirection.LEFT, false);

        // 3. NEU: Animierte Lava-Flüssigkeit für den Tank erstellen
        // (Werte X=176, Y=31, B=14, H=14 sind Schätzwerte für dein PNG!)
        IDrawableStatic staticLava = helper.createDrawable(texture, 176, 31, 14, 14);
        this.animatedLava = helper.createAnimatedDrawable(staticLava, animationDurationInTicks, IDrawableAnimated.StartDirection.BOTTOM, false);

        // 4. NEU: Animierte Brennstoff-Flamme erstellen (brennt von unten nach oben)
        // (Werte X=176, Y=0, B=14, H=14 sind Schätzwerte für dein PNG!)
        IDrawableStatic staticFlame = helper.createDrawable(texture, 176, 0, 14, 14);
        this.animatedFlame = helper.createAnimatedDrawable(staticFlame, animationDurationInTicks, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Override
    public RecipeType<AlloyFurnaceRecipe> getRecipeType() { return TYPE; }

    @Override
    public Component getTitle() { return Component.literal("Alloy Furnace"); }

    @Override
    public int getWidth() { return this.background.getWidth(); }

    @Override
    public int getHeight() { return this.background.getHeight(); }

    @Override
    public IDrawable getIcon() { return this.icon; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AlloyFurnaceRecipe recipe, IFocusGroup focuses) {
        // Die Slot-Koordinaten bleiben gleich, da der Hintergrund-Ausschnitt gleich ist.
        builder.addSlot(RecipeIngredientRole.INPUT, 40, 3).addIngredients(recipe.input1());
        builder.addSlot(RecipeIngredientRole.INPUT, 40, 39).addIngredients(recipe.input2());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 94, 21).addItemStack(recipe.output());
    }

    @Override
    public void draw(AlloyFurnaceRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        // 1. Hintergrund zeichnen
        this.background.draw(guiGraphics);

        // 2. NEU: Animierte Lava-Flüssigkeit zeichnen (am Tank)
        // Position X=2, Y=2 relativ zum gezeichneten 120x60 Hintergrund
        this.animatedLava.draw(guiGraphics, 2, 2);

        // 3. NEU: Animierte Brennstoff-Flamme zeichnen (am Fuel-Slot)
        // Position X=15, Y=33 relativ zum gezeichneten 120x60 Hintergrund
        this.animatedFlame.draw(guiGraphics, 23, 23);

        // 4. Animierter Pfeil zeichnen (mit korrigiertem X-Wert!)
        // Position X=50 (vorher 39), Y=19 relativ zum gezeichneten 120x60 Hintergrund
        this.animatedArrow.draw(guiGraphics, 61, 21);

        // 5. WICHTIG: Die Dauer als Text wiederherstellen!
        // Position relative to the 120x60 background. Putting it near the arrow point.
        // Das musst du wieder manuell in GIMP ausmessen! Ich habe ihn bei X=100, Y=50 positioniert.
        guiGraphics.drawString(Minecraft.getInstance().font, "5s", 100, 50, 0xFF808080, false);
    }
}