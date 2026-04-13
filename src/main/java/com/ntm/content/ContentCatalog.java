package com.ntm.content;

import com.ntm.content.machine.alloy.AlloyFurnaceContent;
import com.ntm.content.material.titanium.TitaniumContent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.Supplier;

public final class ContentCatalog {
    private ContentCatalog() {
    }

    public static List<Supplier<? extends Item>> basicItems() {
        return List.of(
                TitaniumContent.RAW_TITANIUM,
                TitaniumContent.NUGGET_TITANIUM,
                TitaniumContent.INGOT_TITANIUM,
                AlloyFurnaceContent.INGOT_STEEL
        );
    }

    public static List<Supplier<? extends Block>> oreBlocks() {
        return List.of(
                TitaniumContent.ORE_TITANIUM,
                TitaniumContent.ORE_TITANIUM_DEEPSLATE
        );
    }

    public static List<Supplier<? extends Block>> storageBlocks() {
        return List.of(TitaniumContent.BLOCK_TITANIUM);
    }

    public static List<Supplier<? extends Block>> machineBlocks() {
        return List.of(AlloyFurnaceContent.ALLOY_FURNACE);
    }

    public static List<Supplier<? extends Block>> pickaxeMineableBlocks() {
        return List.of(
                TitaniumContent.ORE_TITANIUM,
                TitaniumContent.ORE_TITANIUM_DEEPSLATE,
                TitaniumContent.BLOCK_TITANIUM,
                AlloyFurnaceContent.ALLOY_FURNACE
        );
    }

    public static List<Supplier<? extends Block>> needsIronToolBlocks() {
        return List.of(
                TitaniumContent.ORE_TITANIUM,
                TitaniumContent.ORE_TITANIUM_DEEPSLATE,
                TitaniumContent.BLOCK_TITANIUM,
                AlloyFurnaceContent.ALLOY_FURNACE
        );
    }

    public static List<Supplier<? extends ItemLike>> creativeTabEntries() {
        return List.of(
                TitaniumContent.RAW_TITANIUM,
                TitaniumContent.NUGGET_TITANIUM,
                TitaniumContent.INGOT_TITANIUM,
                TitaniumContent.BLOCK_TITANIUM_ITEM,
                TitaniumContent.ORE_TITANIUM_ITEM,
                TitaniumContent.ORE_TITANIUM_DEEPSLATE_ITEM,
                AlloyFurnaceContent.INGOT_STEEL,
                AlloyFurnaceContent.ALLOY_FURNACE_ITEM
        );
    }
}
