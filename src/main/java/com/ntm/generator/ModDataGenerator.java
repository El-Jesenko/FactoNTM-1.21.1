package com.ntm.generator;

import com.ntm.main.NTM;
import com.ntm.world.biome.ModBiomeModifiers;
import com.ntm.world.feature.ModConfiguredFeatures;
import com.ntm.world.feature.ModPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = NTM.MODID)
public class ModDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();

        // Hier wird dein ModBlockLootTables-Provider registriert
        generator.addProvider(
                event.includeServer(),
                new LootTableProvider(
                        packOutput,
                        Set.of(),
                        List.of(
                                new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK)
                        ),
                        event.getLookupProvider()
                )
        );

        // WICHTIG: Das hier brauchst du für die WorldGen-Registrierung
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        RegistrySetBuilder builder = new RegistrySetBuilder()
                .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrapConfigured)
                .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrapPlaced)
                .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrapBiomes);

// WorldGen Provider hinzufügen
        generator.addProvider(
                event.includeServer(),
                new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, builder, Set.of(NTM.MODID))
        );
        ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(packOutput, event.getLookupProvider(), event.getExistingFileHelper());
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeClient(), new ModBlockStateProvider(packOutput, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput, event.getLookupProvider()));



    }
}
