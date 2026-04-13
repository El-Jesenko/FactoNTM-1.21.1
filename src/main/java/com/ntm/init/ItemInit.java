package com.ntm.init;

import com.ntm.content.machine.alloy.AlloyFurnaceContent;
import com.ntm.content.material.titanium.TitaniumContent;
import com.ntm.content.registry.ModRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemInit {

    public static final DeferredRegister.Items ITEMS = ModRegistries.ITEMS;


    //region ITEMS
    // All Items here not anywhere else

    public static final DeferredItem<Item> RAW_TITANIUM = TitaniumContent.RAW_TITANIUM;
    public static final DeferredItem<Item> INGOT_TITANIUM = TitaniumContent.INGOT_TITANIUM;
    public static final DeferredItem<Item> NUGGET_TITANIUM = TitaniumContent.NUGGET_TITANIUM;

    public static final DeferredItem<Item> INGOT_STEEL = AlloyFurnaceContent.INGOT_STEEL;

    //endregion

    //region REGISTRY

    @Deprecated(since = "1.0.0", forRemoval = false)
    public static void register(IEventBus eventBus)
    {
        // Deprecated shim for compatibility. Registration happens centrally through ModContent.register(...).
    }


    //endregion

}
