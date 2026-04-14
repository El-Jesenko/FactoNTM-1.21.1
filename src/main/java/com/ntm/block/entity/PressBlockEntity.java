package com.ntm.block.entity;

import com.ntm.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PressBlockEntity extends BlockEntity {
    public boolean isWorking = false;
    public int progress = 0;
    public int maxProgress = 100;

    public PressBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PRESS_BE.get(), pos, state);
    }

    public void tick() {
        if (level == null || level.isClientSide) return;

        // TODO: Hier deine Rezept-Logik einfügen (wie beim Alloy Furnace)
        // Platzhalter-Logik für die Animation:
        if (/* hat Rezept */ true) {
            isWorking = true;
            progress++;
            if (progress >= maxProgress) {
                // craftItem();
                progress = 0;
            }
        } else {
            isWorking = false;
            progress = 0;
        }

        // Status für Renderer updaten, falls nötig
    }
}