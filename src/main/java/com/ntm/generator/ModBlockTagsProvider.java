package com.ntm.generator;

import com.ntm.content.ContentCatalog;
import com.ntm.main.NTM;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, NTM.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        var pickaxeTag = this.tag(BlockTags.MINEABLE_WITH_PICKAXE);
        ContentCatalog.pickaxeMineableBlocks().forEach(block -> pickaxeTag.add(block.get()));

        var ironTag = this.tag(BlockTags.NEEDS_IRON_TOOL);
        ContentCatalog.needsIronToolBlocks().forEach(block -> ironTag.add(block.get()));
    }
}
