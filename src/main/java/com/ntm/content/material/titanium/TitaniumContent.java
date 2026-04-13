package com.ntm.content.material.titanium;

import com.ntm.content.registry.ModRegistries;
import com.ntm.content.registry.RegistrationHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public final class TitaniumContent {
    public static final DeferredItem<Item> RAW_TITANIUM = ModRegistries.ITEMS.registerSimpleItem("raw_titanium", new Item.Properties());
    public static final DeferredItem<Item> INGOT_TITANIUM = ModRegistries.ITEMS.registerSimpleItem("ingot_titanium", new Item.Properties());
    public static final DeferredItem<Item> NUGGET_TITANIUM = ModRegistries.ITEMS.registerSimpleItem("nugget_titanium", new Item.Properties());

    private static final RegistrationHelper.BlockWithItem<Block> ORE_TITANIUM_REG = RegistrationHelper.registerBlockWithItem("ore_titanium", () ->
            new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(3.0F, 3.0F)
                    .sound(SoundType.STONE)
                    .noOcclusion()));

    private static final RegistrationHelper.BlockWithItem<Block> ORE_TITANIUM_DEEPSLATE_REG = RegistrationHelper.registerBlockWithItem("ore_titanium_deepslate", () ->
            new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DEEPSLATE)
                    .requiresCorrectToolForDrops()
                    .strength(4.0F, 5.0F)
                    .sound(SoundType.DEEPSLATE)
                    .noOcclusion()));

    private static final RegistrationHelper.BlockWithItem<Block> BLOCK_TITANIUM_REG = RegistrationHelper.registerBlockWithItem("block_titanium", () ->
            new Block(BlockBehaviour.Properties.of()
                    .requiresCorrectToolForDrops()
                    .strength(4.0F, 5.0F)
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> ORE_TITANIUM = ORE_TITANIUM_REG.block();
    public static final DeferredItem<net.minecraft.world.item.BlockItem> ORE_TITANIUM_ITEM = ORE_TITANIUM_REG.item();
    public static final DeferredBlock<Block> ORE_TITANIUM_DEEPSLATE = ORE_TITANIUM_DEEPSLATE_REG.block();
    public static final DeferredItem<net.minecraft.world.item.BlockItem> ORE_TITANIUM_DEEPSLATE_ITEM = ORE_TITANIUM_DEEPSLATE_REG.item();
    public static final DeferredBlock<Block> BLOCK_TITANIUM = BLOCK_TITANIUM_REG.block();
    public static final DeferredItem<net.minecraft.world.item.BlockItem> BLOCK_TITANIUM_ITEM = BLOCK_TITANIUM_REG.item();

    private TitaniumContent() {
    }
}
