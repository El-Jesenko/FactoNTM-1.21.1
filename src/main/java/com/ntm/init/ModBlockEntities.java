package com.ntm.init;

import com.ntm.block.entity.AlloyFurnaceBlockEntity;
import com.ntm.content.machine.alloy.AlloyFurnaceContent;
import com.ntm.content.registry.ModRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = ModRegistries.BLOCK_ENTITIES;
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AlloyFurnaceBlockEntity>> ALLOY_FURNACE_BE = AlloyFurnaceContent.ALLOY_FURNACE_BE;
}
