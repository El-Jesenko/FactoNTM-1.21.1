package com.ntm.generator;

import com.ntm.init.ItemInit;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, "ntm", existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Generiert automatisch ein 2D-Item-Modell
        basicItem(ItemInit.RAW_TITANIUM.get());
        basicItem(ItemInit.INGOT_TITANIUM.get());
        basicItem(ItemInit.NUGGET_TITANIUM.get());
    }
}