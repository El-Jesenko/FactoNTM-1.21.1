package com.ntm.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ntm.block.entity.PressBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;

public class PressRenderer implements BlockEntityRenderer<PressBlockEntity> {

    public PressRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(PressBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        ModelResourceLocation headLocation = new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath("ntm", "block/press_head"), "standalone");
        BakedModel headModel = Minecraft.getInstance().getModelManager().getModel(headLocation);

        poseStack.pushPose();

        float offset = 0;
        if (entity.isWorking && entity.maxProgress > 0) {
            // Einfache Auf/Ab-Animation (Bsp: Stempel geht bis zur Hälfte nach unten)
            offset = ((float) entity.progress / entity.maxProgress) * 0.5f;

            // Wenn er wieder hochgehen soll in der zweiten Hälfte:
            if (offset > 0.25f) offset = 0.5f - offset;
        }

        poseStack.translate(0, -offset, 0);

        Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(
                poseStack.last(), buffer.getBuffer(RenderType.cutout()),
                null, headModel, 1.0F, 1.0F, 1.0F, packedLight, packedOverlay
        );

        poseStack.popPose();
    }
}