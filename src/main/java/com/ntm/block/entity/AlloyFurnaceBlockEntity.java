package com.ntm.block.entity;

import com.ntm.block.AlloyFurnaceBlock;
import com.ntm.init.ItemInit;
import com.ntm.init.ModBlockEntities;
import com.ntm.init.RecipeInit;
import com.ntm.menu.AlloyFurnaceMenu;
import com.ntm.recipe.AlloyFurnaceRecipe;
import com.ntm.recipe.AlloyRecipeInput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;


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
            hasChanged = true; // State-Change bedeutet auch NBT-Change!
        }

        if (hasChanged) setChanged(); // Nur noch EINMAL aufrufen
    }

    // 1. Sucht das passende Rezept
    private Optional<RecipeHolder<AlloyFurnaceRecipe>> getCurrentRecipe() {
        if (level == null) return Optional.empty();
        AlloyRecipeInput input = new AlloyRecipeInput(inventory.getStackInSlot(1), inventory.getStackInSlot(2));
        return level.getRecipeManager().getRecipeFor(RecipeInit.ALLOY_TYPE.get(), input, level);
    }

    // 2. Prüft, ob das Rezept gültig ist und Platz hat
    private boolean hasRecipe() {
        Optional<RecipeHolder<AlloyFurnaceRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return false;

        ItemStack result = recipe.get().value().getResultItem(level.registryAccess());
        ItemStack outputSlot = inventory.getStackInSlot(3);

        return outputSlot.isEmpty() || (ItemStack.isSameItemSameComponents(outputSlot, result) && outputSlot.getCount() + result.getCount() <= outputSlot.getMaxStackSize());
    }

    // 3. Führt das Crafting aus
    private void craftItem() {
        Optional<RecipeHolder<AlloyFurnaceRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return;

        ItemStack result = recipe.get().value().getResultItem(level.registryAccess());

        inventory.getStackInSlot(1).shrink(1);
        inventory.getStackInSlot(2).shrink(1);

        ItemStack outputSlot = inventory.getStackInSlot(3);
        if (outputSlot.isEmpty()) {
            inventory.setStackInSlot(3, result.copy());
        } else {
            outputSlot.grow(result.getCount());
        }
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
        tag.putBoolean("isWorking", isWorking); // WICHTIG: Status speichern
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("progress");
        fuelTime = tag.getInt("fuelTime");
        maxFuelTime = tag.getInt("maxFuelTime");
        isWorking = tag.getBoolean("isWorking"); // WICHTIG: Status laden
    }
}
