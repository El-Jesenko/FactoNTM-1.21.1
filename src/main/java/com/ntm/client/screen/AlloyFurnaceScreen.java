package com.ntm.client.screen;

import com.ntm.main.NTM;
import com.ntm.menu.AlloyFurnaceMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AlloyFurnaceScreen extends AbstractContainerScreen<AlloyFurnaceMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(NTM.MODID, "textures/gui/alloy_furnace.png");

    public AlloyFurnaceScreen(AlloyFurnaceMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        boolean isCrafting = menu.isCrafting(); // Methode in deinem Menu, die prüft, ob gerade geschmolzen wird
        if (isCrafting) {
            guiGraphics.blit(TEXTURE, x + 63, y + 38, 176, 0, 14, 14); // Statischer Pfeil (nur sichtbar, wenn isCrafting true ist)
        }

        // Beispiel für den Pfeil (X, Y auf dem GUI | X, Y von der Textur-Datei)
        int progress = menu.getScaledProgress();
        guiGraphics.blit(TEXTURE, x + 101, y + 36, 176, 14, progress, 17); // <- Werte an dein PNG anpassen

        // Beispiel für den Fuel-Balken (malt von unten nach oben)
        int fuel = menu.getScaledFuel();
        int maxHeight = 54; // Wenn dein Tank exakt 50 Pixel hoch ist

        guiGraphics.blit(TEXTURE,
                x + 43, y + 17 + (maxHeight - fuel),
                200, 0 + (maxHeight - fuel), // <--- HIER: Die '0' muss auch mitwandern!
                18, fuel);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY); // Zeigt Item-Namen an
    }
}
