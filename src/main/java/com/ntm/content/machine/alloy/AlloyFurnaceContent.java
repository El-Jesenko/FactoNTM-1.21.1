package com.ntm.content.machine.alloy;

import com.ntm.block.AlloyFurnaceBlock;
import com.ntm.block.entity.AlloyFurnaceBlockEntity;
import com.ntm.content.registry.ModRegistries;
import com.ntm.content.registry.RegistrationHelper;
import com.ntm.menu.AlloyFurnaceMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

public final class AlloyFurnaceContent {
    public static final DeferredItem<Item> INGOT_STEEL = ModRegistries.ITEMS.registerSimpleItem("ingot_steel", new Item.Properties());

    private static final RegistrationHelper.BlockWithItem<AlloyFurnaceBlock> ALLOY_FURNACE_REG =
            RegistrationHelper.registerBlockWithItem("alloy_furnace", () -> new AlloyFurnaceBlock(BlockBehaviour.Properties.of()
                    .strength(3.5f)
                    .lightLevel(state -> state.hasProperty(AlloyFurnaceBlock.LIT) && state.getValue(AlloyFurnaceBlock.LIT) ? 13 : 0)
                    .requiresCorrectToolForDrops()));

    public static final DeferredBlock<AlloyFurnaceBlock> ALLOY_FURNACE = ALLOY_FURNACE_REG.block();
    public static final DeferredItem<net.minecraft.world.item.BlockItem> ALLOY_FURNACE_ITEM = ALLOY_FURNACE_REG.item();

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AlloyFurnaceBlockEntity>> ALLOY_FURNACE_BE =
            ModRegistries.BLOCK_ENTITIES.register("alloy_furnace", () -> BlockEntityType.Builder.of(AlloyFurnaceBlockEntity::new, ALLOY_FURNACE.get()).build(null));

    public static final DeferredHolder<MenuType<?>, MenuType<AlloyFurnaceMenu>> ALLOY_FURNACE_MENU =
            ModRegistries.MENUS.register("alloy_furnace_menu", () -> IMenuTypeExtension.create(AlloyFurnaceMenu::new));

    private AlloyFurnaceContent() {
    }
}
