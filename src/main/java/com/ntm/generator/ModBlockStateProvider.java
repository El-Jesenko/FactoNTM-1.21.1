package com.ntm.generator;

import com.ntm.content.ContentCatalog;
import com.ntm.init.BlockInit;
import com.ntm.main.NTM;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, NTM.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ContentCatalog.storageBlocks().forEach(block -> simpleBlockWithItem(block.get(), cubeAllFor(block.get())));

        BlockModelBuilder ore_titanium = models().withExistingParent("ore_titanium", mcLoc("block/block"))
                .texture("particle", "minecraft:block/stone")
                .texture("all", "minecraft:block/stone")
                .texture("overlay", "ntm:block/ore_titanium")
                .element().from(0, 0, 0).to(16, 16, 16).allFaces((dir, face) -> face.texture("#all").cullface(dir)).end()
                .element().from(0, 0, 0).to(16, 16, 16).allFaces((dir, face) -> face.texture("#overlay").cullface(dir)).end();

        simpleBlockWithItem(BlockInit.ORE_TITANIUM.get(), ore_titanium);

        BlockModelBuilder ore_titanium_deepslate = models().withExistingParent("ore_titanium_deepslate", mcLoc("block/block"))
                .texture("particle", "minecraft:block/deepslate")
                .texture("all", "minecraft:block/deepslate")
                .texture("overlay", "ntm:block/ore_titanium")
                .element().from(0, 0, 0).to(16, 16, 16).allFaces((dir, face) -> face.texture("#all").cullface(dir)).end()
                .element().from(0, 0, 0).to(16, 16, 16).allFaces((dir, face) -> face.texture("#overlay").cullface(dir)).end();

        simpleBlockWithItem(BlockInit.ORE_TITANIUM_DEEPSLATE.get(), ore_titanium_deepslate);
        registerAlloyFurnace();

    }

    private BlockModelBuilder cubeAllFor(net.minecraft.world.level.block.Block block) {
        String id = BuiltInRegistries.BLOCK.getKey(block).getPath();
        return models().cubeAll(id, modLoc("block/" + id));
    }

    private void registerAlloyFurnace() {
        ResourceLocation side = modLoc("block/machine/alloy_furnace_side_alt");
        ResourceLocation frontOff = modLoc("block/machine/alloy_furnace_front_off_alt");
        ResourceLocation frontOn = modLoc("block/machine/alloy_furnace_front_on_alt");
        ResourceLocation topOff = modLoc("block/machine/alloy_furnace_top_off_alt");
        ResourceLocation topOn = modLoc("block/machine/alloy_furnace_top_on_alt");

        // 1. Die Modelle generieren und zwischenspeichern
        var furnaceOff = models().orientable("alloy_furnace", side, frontOff, topOff);
        var furnaceOn = models().orientable("alloy_furnace_on", side, frontOn, topOn);

        // 2. Den BlockState registrieren (drehbar + an/aus)
        horizontalBlock(BlockInit.ALLOY_FURNACE.get(), state -> {
            return state.getValue(BlockStateProperties.LIT) ? furnaceOn : furnaceOff;
        });
        simpleBlockItem(BlockInit.ALLOY_FURNACE.get(), furnaceOff);
    }
}
