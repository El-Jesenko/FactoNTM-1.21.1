package com.ntm.generator;

import com.ntm.init.BlockInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        // "ntm" ist deine Mod-ID
        super(output, lookupProvider, "ntm", existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Fügt das Tag "minecraft:mineable/pickaxe" hinzu
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(BlockInit.ORE_TITANIUM.get(), BlockInit.ORE_TITANIUM_DEEPSLATE.get(), BlockInit.BLOCK_TITANIUM.get());

        // Fügt das Tag "minecraft:needs_iron_tool" hinzu (Abbau-Level)
        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(BlockInit.ORE_TITANIUM.get(), BlockInit.ORE_TITANIUM_DEEPSLATE.get(), BlockInit.BLOCK_TITANIUM.get());
    }
}