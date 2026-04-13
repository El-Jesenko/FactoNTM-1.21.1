package com.ntm.menu;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, "ntm");

    public static final Supplier<MenuType<AlloyFurnaceMenu>> ALLOY_FURNACE_MENU = MENUS.register("alloy_furnace_menu",
            () -> IMenuTypeExtension.create(AlloyFurnaceMenu::new));
}