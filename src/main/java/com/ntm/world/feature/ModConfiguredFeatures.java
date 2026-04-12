package com.ntm.world.feature;

import com.ntm.init.BlockInit;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> TITANIUM_ORE_KEY = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath("ntm", "ore_titanium"));



    public static void bootstrapConfigured(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        // Beide RuleTests INNERHALB der Methode definieren
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> targets = List.of(
                OreConfiguration.target(stoneReplaceables, BlockInit.ORE_TITANIUM.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, BlockInit.ORE_TITANIUM_DEEPSLATE.get().defaultBlockState())
        );

        context.register(TITANIUM_ORE_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(targets, 9)));
    }
}
