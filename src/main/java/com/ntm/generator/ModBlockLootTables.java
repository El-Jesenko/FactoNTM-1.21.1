package com.ntm.generator;
import com.ntm.content.ContentCatalog;
import com.ntm.init.BlockInit;
import com.ntm.init.ItemInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {

    public ModBlockLootTables(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected void generate() {
        ContentCatalog.oreBlocks().forEach(ore -> this.add(ore.get(), block -> this.createOreDrop(block, ItemInit.RAW_TITANIUM.get())));
        ContentCatalog.storageBlocks().forEach(block -> this.dropSelf(block.get()));
        ContentCatalog.machineBlocks().forEach(block -> this.dropSelf(block.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BlockInit.BLOCKS.getEntries().stream().map(e -> (Block) e.value())::iterator;
    }
}
