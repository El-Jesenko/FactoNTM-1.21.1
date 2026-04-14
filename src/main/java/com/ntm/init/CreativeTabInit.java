package com.ntm.init;

import com.ntm.main.NTM;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;



public class CreativeTabInit {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NTM.MODID);

    //region CREATIVE-TABS

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> NTM_TAB = TABS.register("ntm_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.ntm"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ItemInit.RAW_TITANIUM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ItemInit.RAW_TITANIUM.get());
                output.accept(ItemInit.NUGGET_TITANIUM.get());
                output.accept(ItemInit.INGOT_TITANIUM.get());
                output.accept(BlockInit.BLOCK_TITANIUM_ITEM.get());
                output.accept(BlockInit.ORE_TITANIUM_ITEM.get());
                output.accept(BlockInit.ORE_TITANIUM_DEEPSLATE_ITEM.get());
                output.accept(ItemInit.INGOT_STEEL.get());
                output.accept(BlockInit.ALLOY_FURNACE_ITEM.get());
                output.accept(BlockInit.PRESS_ITEM.get());
            }).build());


    //endregion


    public static void register(IEventBus eventBus)
    {
        TABS.register(eventBus);
    }

}
