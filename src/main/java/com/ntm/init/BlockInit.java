package com.ntm.init;

import com.ntm.content.machine.alloy.AlloyFurnaceContent;
import com.ntm.content.material.titanium.TitaniumContent;
import com.ntm.content.registry.ModRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockInit {
    public static final DeferredRegister.Blocks BLOCKS = ModRegistries.BLOCKS;

    //region BLOCKS


    public static final DeferredBlock<? extends Block> ORE_TITANIUM = TitaniumContent.ORE_TITANIUM;
    public static final DeferredItem<BlockItem> ORE_TITANIUM_ITEM = TitaniumContent.ORE_TITANIUM_ITEM;
    public static final DeferredBlock<? extends Block> ORE_TITANIUM_DEEPSLATE = TitaniumContent.ORE_TITANIUM_DEEPSLATE;
    public static final DeferredItem<BlockItem> ORE_TITANIUM_DEEPSLATE_ITEM = TitaniumContent.ORE_TITANIUM_DEEPSLATE_ITEM;
    public static final DeferredBlock<? extends Block> BLOCK_TITANIUM = TitaniumContent.BLOCK_TITANIUM;
    public static final DeferredItem<BlockItem> BLOCK_TITANIUM_ITEM = TitaniumContent.BLOCK_TITANIUM_ITEM;
    public static final DeferredBlock<? extends Block> ALLOY_FURNACE = AlloyFurnaceContent.ALLOY_FURNACE;
    public static final DeferredItem<BlockItem> ALLOY_FURNACE_ITEM = AlloyFurnaceContent.ALLOY_FURNACE_ITEM;

    //endregion

    public static void register(IEventBus eventBus)
    {
        // Registration happens centrally through ModContent.register(...)
    }

}
