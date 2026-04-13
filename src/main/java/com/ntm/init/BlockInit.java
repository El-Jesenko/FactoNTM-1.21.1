package com.ntm.init;

import com.ntm.block.AlloyFurnaceBlock;
import com.ntm.main.NTM;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.swing.*;

public class BlockInit {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NTM.MODID);

    //region BLOCKS


    public static final DeferredBlock<Block> ORE_TITANIUM = BLOCKS.register("ore_titanium", () -> new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .strength(3.0F,3.0F)
            .sound(SoundType.STONE)
            .noOcclusion()));

    public static final DeferredItem<BlockItem> ORE_TITANIUM_ITEM = ItemInit.ITEMS.registerSimpleBlockItem("ore_titanium", ORE_TITANIUM);
    public static final DeferredBlock<Block> ORE_TITANIUM_DEEPSLATE = BLOCKS.register("ore_titanium_deepslate", () -> new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.DEEPSLATE)
            .requiresCorrectToolForDrops()
            .strength(4.0F,5.0F)
            .sound(SoundType.DEEPSLATE)
            .noOcclusion()));
    public static final DeferredItem<BlockItem> ORE_TITANIUM_DEEPSLATE_ITEM = ItemInit.ITEMS.registerSimpleBlockItem("ore_titanium_deepslate", ORE_TITANIUM_DEEPSLATE);

    public static final DeferredBlock<Block> BLOCK_TITANIUM = BLOCKS.register("block_titanium", () -> new Block(BlockBehaviour.Properties.of()
            .requiresCorrectToolForDrops()
            .strength(4.0F,5.0F)
            .sound(SoundType.STONE)));

    public static final DeferredItem<BlockItem> BLOCK_TITANIUM_ITEM = ItemInit.ITEMS.registerSimpleBlockItem("block_titanium", BLOCK_TITANIUM);

    public static final DeferredBlock<Block> ALLOY_FURNACE = BLOCKS.registerBlock("alloy_furnace", AlloyFurnaceBlock::new, BlockBehaviour.Properties.of().strength(3.5f).requiresCorrectToolForDrops());
    public static final DeferredItem<BlockItem> ALLOY_FURNACE_ITEM = ItemInit.ITEMS.registerSimpleBlockItem("alloy_furnace", BlockInit.ALLOY_FURNACE);

    //endregion

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }

}
