package com.ntm.menu;

import com.ntm.block.entity.AlloyFurnaceBlockEntity;
import com.ntm.init.BlockInit;
import com.ntm.machine.base.BaseMachineMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class AlloyFurnaceMenu extends BaseMachineMenu {
    public final AlloyFurnaceBlockEntity blockEntity;

    // Wird vom Client aufgerufen
    public AlloyFurnaceMenu(int containerId, Inventory playerInv, FriendlyByteBuf extraData) {
        this(containerId, playerInv, playerInv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(5));
    }

    // Wird vom Server aufgerufen
    public AlloyFurnaceMenu(int containerId, Inventory playerInv, BlockEntity entity, ContainerData data) {
        super(ModMenus.ALLOY_FURNACE_MENU.get(), containerId, ContainerLevelAccess.create(entity.getLevel(), entity.getBlockPos()), data, 4);
        this.blockEntity = (AlloyFurnaceBlockEntity) entity;

        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 0, 8, 36));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 1, 80, 18));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 2, 80, 54));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 3, 134, 36));

        addPlayerInventory(playerInv, 8, 84);
    }

    public int getScaledProgress() {
        int prog = this.data.get(0);
        int max = this.data.get(1);
        int arrowSize = 24;
        return max != 0 && prog != 0 ? prog * arrowSize / max : 0;
    }

    public int getScaledFuel() {
        int fuel = this.data.get(2);
        int max = this.data.get(3);
        int barHeight = 54;
        return max != 0 && fuel != 0 ? fuel * barHeight / max : 0;
    }

    public boolean isCrafting() {
        return this.data.get(4) > 0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return quickMoveMachineItem(player, index, 3);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(player, BlockInit.ALLOY_FURNACE.get());
    }
}
