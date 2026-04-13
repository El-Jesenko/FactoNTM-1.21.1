package com.ntm.menu;

import com.ntm.content.machine.alloy.AlloyFurnaceContent;
import com.ntm.content.registry.ModRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = ModRegistries.MENUS;

    public static final DeferredHolder<MenuType<?>, MenuType<AlloyFurnaceMenu>> ALLOY_FURNACE_MENU = AlloyFurnaceContent.ALLOY_FURNACE_MENU;
}
