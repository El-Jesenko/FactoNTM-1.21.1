package com.ntm.init;

import com.ntm.block.entity.AlloyFurnaceBlockEntity;
import com.ntm.init.BlockInit;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    // "ntm" = deine Mod-ID
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, "ntm");

    public static final Supplier<BlockEntityType<AlloyFurnaceBlockEntity>> ALLOY_FURNACE_BE = BLOCK_ENTITIES.register("alloy_furnace",
            () -> BlockEntityType.Builder.of(AlloyFurnaceBlockEntity::new, BlockInit.ALLOY_FURNACE.get()).build(null));
}