package com.ntm.init;

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

    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ItemInit.ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);


    public static final DeferredBlock<Block> ORE_TITANIUM = BLOCKS.register("ore_titanium", () -> new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .strength(3.0F,3.0F)
            .sound(SoundType.STONE)
            .noOcclusion()));

    public static final DeferredItem<BlockItem> ORE_TITANIUM_ITEM = ItemInit.ITEMS.registerSimpleBlockItem("ore_titanium", ORE_TITANIUM);


    //endregion

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }

}
