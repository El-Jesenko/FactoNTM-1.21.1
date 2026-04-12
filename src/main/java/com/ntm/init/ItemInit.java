package com.ntm.init;

import com.ntm.main.NTM;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemInit {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NTM.MODID);


    //region ITEMS
    // All Items here not anywhere else

    public static final DeferredItem<Item> RAW_TITANIUM = ITEMS.registerSimpleItem("raw_titanium", new Item.Properties());
    public static final DeferredItem<Item> INGOT_TITANIUM = ITEMS.registerSimpleItem("ingot_titanium", new Item.Properties());

    public static final DeferredItem<Item> NUGGET_TITANIUM = ITEMS.registerSimpleItem("nugget_titanium", new Item.Properties());

    //endregion

    //region REGISTRY

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }


    //endregion

}
