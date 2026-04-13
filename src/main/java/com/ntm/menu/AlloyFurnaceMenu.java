package com.ntm.menu;

import com.ntm.block.entity.AlloyFurnaceBlockEntity;
import com.ntm.init.BlockInit;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class AlloyFurnaceMenu extends AbstractContainerMenu {
    public final AlloyFurnaceBlockEntity blockEntity;
    private final ContainerLevelAccess levelAccess;

    // 1. Client-Konstruktor anpassen (Fügt ein leeres Array für den Client ein)
    public final ContainerData data;

    // 1. Client-Konstruktor anpassen (Fügt ein leeres Array für den Client ein)
    public AlloyFurnaceMenu(int containerId, Inventory playerInv, FriendlyByteBuf extraData) {
        this(containerId, playerInv, playerInv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(4));
    }

    // Wird vom Server aufgerufen
    public AlloyFurnaceMenu(int containerId, Inventory playerInv, BlockEntity entity, ContainerData data) {        super(ModMenus.ALLOY_FURNACE_MENU.get(), containerId);
        this.blockEntity = (AlloyFurnaceBlockEntity) entity;
        this.levelAccess = ContainerLevelAccess.create(entity.getLevel(), entity.getBlockPos());

        // Maschinen-Slots (X, Y auf deiner Textur)
        this.addSlot(new SlotItemHandler(blockEntity.inventory, 0, 8, 36)); // Fuel
        this.addSlot(new SlotItemHandler(blockEntity.inventory, 1, 80, 18)); // Input 1
        this.addSlot(new SlotItemHandler(blockEntity.inventory, 2, 80, 54)); // Input 2
        this.addSlot(new SlotItemHandler(blockEntity.inventory, 3, 134, 36)); // Output

        // Spieler-Inventar (Standard-Werte)
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInv, k, 8 + k * 18, 142));
        }
        this.data = data;
        this.addDataSlots(data); // Das überträgt die Daten automatisch!
    }

    // 3. Hilfsmethoden für den Screen (unten einfügen)
    public int getScaledProgress() {
        int prog = this.data.get(0);
        int max = this.data.get(1);
        int arrowSize = 24; // Breite deines Pfeils in Pixeln
        return max != 0 && prog != 0 ? prog * arrowSize / max : 0;
    }

    public int getScaledFuel() {
        int fuel = this.data.get(2);
        int max = this.data.get(3);
        int barHeight = 54; // Höhe deines Lava-Balkens in Pixeln
        return max != 0 && fuel != 0 ? fuel * barHeight / max : 0;
    }

    public boolean isCrafting() {
        return this.data.get(0) > 0;
    }

    // Konstanten zur besseren Übersicht der Slot-Indexe
    private static final int MACHINE_SLOT_COUNT = 4;
    private static final int PLAYER_INVENTORY_FIRST_SLOT = 4;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = 36; // 27 (Inventar) + 9 (Hotbar)

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot sourceSlot = this.slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // 1. Klick war im Spieler-Inventar (Index 4 bis 39)
        if (index >= PLAYER_INVENTORY_FIRST_SLOT) {
            // Versuche, das Item in die Maschine zu verschieben (Slots 0 bis 2: Fuel, Input 1, Input 2)
            // Wir lassen Slot 3 (Output) absichtlich aus, damit man da nichts reinmogeln kann!
            if (!this.moveItemStackTo(sourceStack, 0, 3, false)) {
                // Wenn die Maschine voll ist, versuche zwischen Hotbar und Hauptinventar zu tauschen
                if (index < PLAYER_INVENTORY_FIRST_SLOT + 27) { // Hauptinventar
                    if (!this.moveItemStackTo(sourceStack, PLAYER_INVENTORY_FIRST_SLOT + 27, PLAYER_INVENTORY_FIRST_SLOT + PLAYER_INVENTORY_SLOT_COUNT, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(sourceStack, PLAYER_INVENTORY_FIRST_SLOT, PLAYER_INVENTORY_FIRST_SLOT + 27, false)) { // Hotbar
                    return ItemStack.EMPTY;
                }
            }
        }
        // 2. Klick war in der Maschine (Index 0 bis 3)
        else {
            // Versuche, das Item zurück zum Spieler zu verschieben
            if (!this.moveItemStackTo(sourceStack, PLAYER_INVENTORY_FIRST_SLOT, PLAYER_INVENTORY_FIRST_SLOT + PLAYER_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        }

        // Standard-Inventar-Updates von Minecraft
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.levelAccess, player, BlockInit.ALLOY_FURNACE.get());
    }
}