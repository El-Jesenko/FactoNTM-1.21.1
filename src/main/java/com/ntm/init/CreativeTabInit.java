package com.ntm.init;

import com.ntm.content.ContentCatalog;
import com.ntm.content.registry.ModRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;



public class CreativeTabInit {
    public static final DeferredRegister<CreativeModeTab> TABS = ModRegistries.TABS;

    //region CREATIVE-TABS

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> NTM_TAB = TABS.register("ntm_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.ntm"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ItemInit.RAW_TITANIUM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                ContentCatalog.creativeTabEntries().forEach(entry -> output.accept(entry.get()));
            }).build());


    //endregion


    @Deprecated(forRemoval = false)
    public static void register(IEventBus eventBus)
    {
        // Deprecated shim for compatibility. Registration happens centrally through ModContent.register(...).
    }

}
