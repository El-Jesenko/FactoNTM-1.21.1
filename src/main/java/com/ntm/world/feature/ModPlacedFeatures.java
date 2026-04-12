package com.ntm.world.feature;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static com.ntm.world.feature.ModConfiguredFeatures.TITANIUM_ORE_KEY;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> TITANIUM_ORE_PLACED_KEY = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath("ntm", "ore_titanium_placed"));

    public static void bootstrapPlaced(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        List<PlacementModifier> placement = List.of(
                CountPlacement.of(7), // 7 Adern pro Chunk
                InSquarePlacement.spread(),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80)), // Spawnt zwischen Y = -64 und Y = 80
                BiomeFilter.biome()
        );

        context.register(TITANIUM_ORE_PLACED_KEY, new PlacedFeature(configuredFeatures.getOrThrow(TITANIUM_ORE_KEY), placement));
    }
}
