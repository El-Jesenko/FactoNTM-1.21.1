package com.ntm.generator;

import com.ntm.content.ContentCatalog;
import com.ntm.main.NTM;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, NTM.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ContentCatalog.basicItems().forEach(item -> basicItem(item.get()));
    }
}
