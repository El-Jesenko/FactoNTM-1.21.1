package com.ntm.block.entity;

import com.ntm.block.AlloyFurnaceBlock;
import com.ntm.init.ItemInit;
import com.ntm.init.ModBlockEntities;
import com.ntm.menu.AlloyFurnaceMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.Nullable;


public class AlloyFurnaceBlockEntity extends BlockEntity implements MenuProvider {

    public final ItemStackHandler inventory = new ItemStackHandler(4)
    {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    public boolean isWorking= false;
    public int progress = 0;
    public int maxProgress = 100;
    public int fuelTime = 0;
    public int maxFuelTime = 0;

    public static final int MAX_FUEL = 20000; // Entspricht ca. 1 Lavaeimer

        // Deine ContainerData anpassen:
        public final ContainerData data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> progress;
                    case 1 -> maxProgress;
                    case 2 -> fuelTime;
                    case 3 -> MAX_FUEL;
                    case 4 -> isWorking ? 1 : 0; // NEU: 1 ist true, 0 ist false
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> progress = value;
                    case 1 -> maxProgress = value;
                    case 2 -> fuelTime = value;
                    case 4 -> isWorking = value > 0; // NEU
                }
            }

        @Override
        public int getCount() { return 5; }
    };

    public AlloyFurnaceBlockEntity(BlockPos pos, BlockState state) {
        // ModBlockEntities.ALLOY_FURNACE_BE existiert noch nicht, das machen wir im nächsten Schritt
        super(ModBlockEntities.ALLOY_FURNACE_BE.get(), pos, state);
    }

    public void tick() {
        if (level.isClientSide()) return;
        boolean hasChanged = false;

        // 1. Brennstoff aufsaugen (NTM Style)
        ItemStack fuelStack = inventory.getStackInSlot(0);
        if (!fuelStack.isEmpty()) {
            int burnTime = fuelStack.getBurnTime(net.minecraft.world.item.crafting.RecipeType.SMELTING);
            // Prüfen, ob noch Platz im "Tank" ist
            if (burnTime > 0 && fuelTime + burnTime <= MAX_FUEL) {
                fuelTime += burnTime;
                fuelStack.shrink(1);
                hasChanged = true;
            }
        }

        // 2. Arbeiten (Schmelzen)
        if (hasRecipe() && fuelTime > 0) {
            isWorking = true;
            progress++;
            fuelTime--; // Verbraucht 1 Fuel pro Tick während der Arbeit
            if (progress >= maxProgress) {
                craftItem();
                progress = 0;
            }
            hasChanged = true;
        } else {
            isWorking = false;
            if (progress > 0) {
                progress = 0;
                hasChanged = true;
            }
        }

        // 3. BlockState updaten (Nutzt jetzt die neue isWorking Variable)
        BlockState currentState = getBlockState();
        if (currentState.getValue(AlloyFurnaceBlock.LIT) != isWorking) {
            level.setBlock(getBlockPos(), currentState.setValue(AlloyFurnaceBlock.LIT, isWorking), 3);
        }

        if (hasChanged) setChanged();

        if (hasChanged) setChanged();
    }

    // Platzhalter-Rezept: 1 Eisen + 1 Kohle = 1 Titanium Ingot
    private boolean hasRecipe() {
        boolean hasInput1 = inventory.getStackInSlot(1).is(Items.IRON_INGOT);
        boolean hasInput2 = inventory.getStackInSlot(2).is(Items.COAL);
        ItemStack outputSlot = inventory.getStackInSlot(3);

        // Prüfen, ob Inputs stimmen UND Output leer ist (oder Platz hat)
        return hasInput1 && hasInput2 && (outputSlot.isEmpty() || outputSlot.getCount() < outputSlot.getMaxStackSize());
    }

    private void craftItem() {
        inventory.getStackInSlot(1).shrink(1);
        inventory.getStackInSlot(2).shrink(1);

        ItemStack output = new ItemStack(ItemInit.INGOT_STEEL.get(), 1); // Dein Output-Item
        inventory.insertItem(3, output, false);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Alloy Furnace"); // Oder translatable("block.ntm.alloy_furnace")
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new AlloyFurnaceMenu(containerId, playerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.putInt("progress", progress);
        tag.putInt("fuelTime", fuelTime);
        tag.putInt("maxFuelTime", maxFuelTime);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("progress");
        fuelTime = tag.getInt("fuelTime");
        maxFuelTime = tag.getInt("maxFuelTime");
    }
}
