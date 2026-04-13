package com.ntm.main;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_DEBUG_LOGS = BUILDER
            .comment("Enable extra development logging.")
            .define("enableDebugLogs", false);

    public static final ModConfigSpec.IntValue DEFAULT_MACHINE_PROCESS_TIME = BUILDER
            .comment("Default fallback process time in ticks for simple machine logic.")
            .defineInRange("defaultMachineProcessTime", 100, 1, Integer.MAX_VALUE);

    public static final ModConfigSpec.ConfigValue<String> DEBUG_LOG_PREFIX = BUILDER
            .comment("Prefix for debug logs.")
            .define("debugLogPrefix", "[NTM] ");

    public static final ModConfigSpec.ConfigValue<List<? extends String>> DEBUG_ITEM_IDS = BUILDER
            .comment("Optional list of item IDs to inspect in debug mode.")
            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), () -> "", Config::validateItemName);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }
}
