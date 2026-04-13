package com.ntm.machine.base;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public abstract class BaseMachineMenu extends AbstractContainerMenu {
    protected static final int PLAYER_INVENTORY_SLOT_COUNT = 36;
    protected final ContainerLevelAccess levelAccess;
    protected final ContainerData data;
    protected final int machineSlotCount;

    protected BaseMachineMenu(MenuType<?> menuType, int containerId, ContainerLevelAccess levelAccess, ContainerData data, int machineSlotCount) {
        super(menuType, containerId);
        this.levelAccess = levelAccess;
        this.data = data;
        this.machineSlotCount = machineSlotCount;
        this.addDataSlots(data);
    }

    protected void addPlayerInventory(Inventory playerInv, int leftCol, int topRow) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9, leftCol + col * 18, topRow + row * 18));
            }
        }
        for (int hotbar = 0; hotbar < 9; ++hotbar) {
            this.addSlot(new Slot(playerInv, hotbar, leftCol + hotbar * 18, topRow + 58));
        }
    }

    protected ItemStack quickMoveMachineItem(Player player, int index, int machineInputRangeEndExclusive) {
        Slot sourceSlot = this.slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copy = sourceStack.copy();

        int playerStart = machineSlotCount;
        int playerEnd = machineSlotCount + PLAYER_INVENTORY_SLOT_COUNT;

        if (index >= playerStart) {
            if (!this.moveItemStackTo(sourceStack, 0, machineInputRangeEndExclusive, false)) {
                int mainInvEnd = playerStart + 27;
                if (index < mainInvEnd) {
                    if (!this.moveItemStackTo(sourceStack, mainInvEnd, playerEnd, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(sourceStack, playerStart, mainInvEnd, false)) {
                    return ItemStack.EMPTY;
                }
            }
        } else if (!this.moveItemStackTo(sourceStack, playerStart, playerEnd, false)) {
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(player, sourceStack);
        return copy;
    }

    protected boolean stillValid(Player player, Block block) {
        return stillValid(this.levelAccess, player, block);
    }
}
