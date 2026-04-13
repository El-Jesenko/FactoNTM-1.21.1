package com.ntm.generator;

import com.ntm.init.BlockInit;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, "ntm", exFileHelper); // "ntm" ist deine Mod-ID
    }

    @Override
    protected void registerStatesAndModels() {
        // 1. Normales Titanium (Standard-Würfel)
        simpleBlockWithItem(BlockInit.BLOCK_TITANIUM.get(), models().cubeAll("block_titanium", modLoc("block/block_titanium")));

        BlockModelBuilder ore_titanium = models().withExistingParent("ore_titanium", mcLoc("block/block"))
                .texture("particle", "minecraft:block/stone")
                .texture("all", "minecraft:block/stone")
                .texture("overlay", "ntm:block/ore_titanium")
                .element().from(0, 0, 0).to(16, 16, 16).allFaces((dir, face) -> face.texture("#all").cullface(dir)).end()
                .element().from(0, 0, 0).to(16, 16, 16).allFaces((dir, face) -> face.texture("#overlay").cullface(dir)).end();

        simpleBlockWithItem(BlockInit.ORE_TITANIUM.get(), ore_titanium);

        // 2. Deepslate Titanium (Dein exaktes, komplexes Layer-JSON)
        // Ersetze diese Zeile:
// BlockModelBuilder deepslateModel = models().getBuilder("ore_titanium_deepslate")

// Durch diese:
        BlockModelBuilder ore_titanium_deepslate = models().withExistingParent("ore_titanium_deepslate", mcLoc("block/block"))
                .texture("particle", "minecraft:block/deepslate")
                .texture("all", "minecraft:block/deepslate")
                .texture("overlay", "ntm:block/ore_titanium")
                .element().from(0, 0, 0).to(16, 16, 16).allFaces((dir, face) -> face.texture("#all").cullface(dir)).end()
                .element().from(0, 0, 0).to(16, 16, 16).allFaces((dir, face) -> face.texture("#overlay").cullface(dir)).end();

        simpleBlockWithItem(BlockInit.ORE_TITANIUM_DEEPSLATE.get(), ore_titanium_deepslate);
        registerAlloyFurnace();

    }


    private void registerAlloyFurnace() {
        ResourceLocation side = modLoc("machine/alloy_furnace_side_alt");
        ResourceLocation frontOff = modLoc("machine/alloy_furnace_front_off_alt");
        ResourceLocation frontOn = modLoc("machine/alloy_furnace_front_on_alt");
        ResourceLocation topOff = modLoc("machine/alloy_furnace_top_off_alt");
        ResourceLocation topOn = modLoc("machine/alloy_furnace_top_on_alt");

        horizontalBlock(BlockInit.ALLOY_FURNACE.get(), state -> {
            boolean lit = state.getValue(BlockStateProperties.LIT);
            if (lit) {
                return models().orientable("alloy_furnace_on", side, frontOn, topOn);
            } else {
                return models().orientable("alloy_furnace", side, frontOff, topOff);
            }
        });
    }
}