package com.ntm.main;

import com.mojang.logging.LogUtils;
import com.ntm.client.screen.AlloyFurnaceScreen;
import com.ntm.content.ModContent;
import com.ntm.menu.ModMenus;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.slf4j.Logger;

@Mod(NTM.MODID)
public class NTM {
    public static final String MODID = "ntm";
    public static final Logger LOGGER = LogUtils.getLogger();

    public NTM(IEventBus modEventBus, ModContainer modContainer) {
        ModContent.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @EventBusSubscriber(modid = MODID, value = net.neoforged.api.distmarker.Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenus.ALLOY_FURNACE_MENU.get(), AlloyFurnaceScreen::new);
        }
    }
}
