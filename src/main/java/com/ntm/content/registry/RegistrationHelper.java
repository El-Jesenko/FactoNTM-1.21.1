package com.ntm.content.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.function.Supplier;

public final class RegistrationHelper {
    private RegistrationHelper() {
    }

    public static <T extends Block> BlockWithItem<T> registerBlockWithItem(String name, Supplier<T> blockSupplier) {
        DeferredBlock<T> block = ModRegistries.BLOCKS.register(name, blockSupplier);
        DeferredItem<BlockItem> item = ModRegistries.ITEMS.registerSimpleBlockItem(name, block);
        return new BlockWithItem<>(block, item);
    }

    public record BlockWithItem<T extends Block>(DeferredBlock<T> block, DeferredItem<BlockItem> item) {
    }
}
