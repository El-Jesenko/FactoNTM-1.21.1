package com.ntm.content;

import com.ntm.content.machine.alloy.AlloyFurnaceContent;
import com.ntm.content.material.titanium.TitaniumContent;
import com.ntm.content.registry.ModRegistries;
import com.ntm.init.CreativeTabInit;
import com.ntm.recipe.ModRecipes;
import net.neoforged.bus.api.IEventBus;

public final class ModContent {
    private ModContent() {
    }

    public static void register(IEventBus eventBus) {
        ModRegistries.register(eventBus);
        // Force class initialization of modules so static registrations are created.
        TitaniumContent.RAW_TITANIUM.getId();
        AlloyFurnaceContent.ALLOY_FURNACE.getId();
        ModRecipes.ALLOYING_TYPE.getId();
        CreativeTabInit.NTM_TAB.getId();
    }
}
