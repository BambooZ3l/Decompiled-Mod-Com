/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.GlStateManager$DestFactor
 *  com.mojang.blaze3d.platform.GlStateManager$SourceFactor
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.blaze3d.vertex.VertexFormat
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  com.mojang.math.Matrix4f
 *  com.mojang.math.Vector3f
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderStateShard$CullStateShard
 *  net.minecraft.client.renderer.RenderStateShard$EmptyTextureStateShard
 *  net.minecraft.client.renderer.RenderStateShard$LayeringStateShard
 *  net.minecraft.client.renderer.RenderStateShard$ShaderStateShard
 *  net.minecraft.client.renderer.RenderStateShard$TextureStateShard
 *  net.minecraft.client.renderer.RenderStateShard$TransparencyStateShard
 *  net.minecraft.client.renderer.RenderStateShard$WriteMaskStateShard
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.RenderType$CompositeState
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderer
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.FastColor$ARGB32
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 */
package com.buuz135.portality.proxy.client.render;

import com.buuz135.portality.block.ControllerBlock;
import com.buuz135.portality.tile.ControllerTile;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class TESRPortal
implements BlockEntityRenderer<ControllerTile> {
    public static ResourceLocation TEXTURE = new ResourceLocation("portality", "textures/blocks/portal_render.png");
    private static final Random RANDOM = new Random(31100L);
    public static RenderType TYPE = TESRPortal.createRenderType();

    public TESRPortal(BlockEntityRendererProvider.Context context) {
    }

    public static RenderType createRenderType() {
        RenderType.CompositeState state = RenderType.CompositeState.m_110628_().m_110687_(new RenderStateShard.WriteMaskStateShard(true, true)).m_173292_(new RenderStateShard.ShaderStateShard(GameRenderer::m_172814_)).m_173290_((RenderStateShard.EmptyTextureStateShard)new RenderStateShard.TextureStateShard(TEXTURE, false, false)).m_110685_(new RenderStateShard.TransparencyStateShard("translucent_transparency", () -> {
            RenderSystem.m_69478_();
            RenderSystem.m_69408_((GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE);
        }, () -> {
            RenderSystem.m_69461_();
            RenderSystem.m_69453_();
        })).m_110669_(new RenderStateShard.LayeringStateShard("view_offset_z_layering", () -> {
            PoseStack posestack = RenderSystem.m_157191_();
            posestack.m_85836_();
            posestack.m_85841_(0.99975586f, 0.99975586f, 0.99975586f);
            RenderSystem.m_157182_();
        }, () -> {
            PoseStack posestack = RenderSystem.m_157191_();
            posestack.m_85849_();
            RenderSystem.m_157182_();
        })).m_110661_(new RenderStateShard.CullStateShard(false)).m_110691_(true);
        return RenderType.m_173215_((String)"portal_render", (VertexFormat)DefaultVertexFormat.f_85818_, (VertexFormat.Mode)VertexFormat.Mode.QUADS, (int)256, (boolean)false, (boolean)false, (RenderType.CompositeState)state);
    }

    public void renderTop(PoseStack stack, VertexConsumer buffer, ControllerTile te, float frame, float xTrans, float yTrans, float zTrans, double offset, int width, int color) {
        double scale = 0.9335;
        float y = 3.999f;
        float off = 4.0f - y;
        float red = (float)FastColor.ARGB32.m_13665_((int)color) / 256.0f;
        float green = (float)FastColor.ARGB32.m_13667_((int)color) / 256.0f;
        float blue = (float)FastColor.ARGB32.m_13669_((int)color) / 256.0f;
        for (int posX = 0; posX < width; ++posX) {
            for (int posZ = 0; posZ < te.getLength(); ++posZ) {
                float pX1 = 1.0f;
                float u = 1.0f;
                float pX2 = 0.0f;
                float u2 = 0.0f;
                if (posX == 0) {
                    pX2 = 1.0f - frame;
                    u2 = 1.0f - frame;
                }
                if (posX == 1 && frame < 0.0f) {
                    pX2 = -frame;
                    u2 = -frame;
                }
                if (posX == width - 1) {
                    pX1 = Math.max(1.0f - frame, 0.0f);
                    u = 1.0f - 1.0f * frame;
                }
                boolean alpha = true;
                float xOffset = (float)posX - 2.0f + frame + off + xTrans;
                float yOffset = yTrans - off;
                float zOffset = (float)posZ + zTrans;
                Matrix4f matrix = stack.m_85850_().m_85861_();
                buffer.m_85982_(matrix, pX2 + xOffset, yOffset, 0.0f + zOffset).m_85950_(red, green, blue, (float)alpha).m_7421_(u2, 0.0f).m_5752_();
                buffer.m_85982_(matrix, pX1 + xOffset, yOffset, 0.0f + zOffset).m_85950_(red, green, blue, (float)alpha).m_7421_(u, 0.0f).m_5752_();
                buffer.m_85982_(matrix, pX1 + xOffset, yOffset, 1.0f + zOffset).m_85950_(red, green, blue, (float)alpha).m_7421_(u, 1.0f).m_5752_();
                buffer.m_85982_(matrix, pX2 + xOffset, yOffset, 1.0f + zOffset).m_85950_(red, green, blue, (float)alpha).m_7421_(u2, 1.0f).m_5752_();
            }
        }
    }

    public void render(ControllerTile te, float p_225616_2_, PoseStack matrixStack, MultiBufferSource typeBuffer, int p_225616_5_, int p_225616_6_) {
        BlockState blockState;
        if (!te.isFormed()) {
            return;
        }
        matrixStack.m_85836_();
        float frame = (float)(te.m_58904_().m_46467_() % 60L) / 60.0f;
        int x = 0;
        int y = 0;
        int z = 0;
        if (te.isDisplayNameEnabled() && te.isActive()) {
            matrixStack.m_85836_();
            String name = te.getLinkData().getName();
            matrixStack.m_85837_(0.5, 1.5, 0.5);
            matrixStack.m_85845_(Minecraft.m_91087_().m_91290_().m_114470_());
            matrixStack.m_85841_(-0.025f, -0.025f, 0.025f);
            float f1 = Minecraft.m_91087_().f_91066_.m_92141_(0.25f);
            int j = (int)(f1 * 255.0f) << 24;
            Minecraft.m_91087_().f_91062_.m_92811_(name, (float)(-Minecraft.m_91087_().f_91062_.m_92895_(name)) / 2.0f, 0.0f, -1, false, matrixStack.m_85850_().m_85861_(), typeBuffer, false, j, 0xF000F0);
            matrixStack.m_85849_();
        }
        if (!(blockState = te.m_58904_().m_8055_(te.m_58899_())).m_61138_((Property)ControllerBlock.FACING_HORIZONTAL)) {
            return;
        }
        Direction facing = (Direction)blockState.m_61143_((Property)ControllerBlock.FACING_HORIZONTAL);
        if (facing == Direction.SOUTH) {
            z = -1;
            x = -1;
            matrixStack.m_85845_(Vector3f.f_122225_.m_122240_(-180.0f));
        }
        if (facing == Direction.EAST) {
            z = -1;
            matrixStack.m_85845_(Vector3f.f_122225_.m_122240_(-90.0f));
        }
        if (facing == Direction.WEST) {
            x = -1;
            matrixStack.m_85845_(Vector3f.f_122225_.m_122240_(90.0f));
        }
        if (facing == Direction.NORTH) {
            // empty if block
        }
        VertexConsumer buffer = typeBuffer.m_6299_(TYPE);
        this.renderTop(matrixStack, buffer, te, frame, (float)(-te.getWidth()) + 2.0f + (float)x, (float)(te.getHeight() + y) - 1.0f, z, 0.4, te.getWidth() * 2, te.getColor());
        matrixStack.m_85845_(Vector3f.f_122227_.m_122240_(90.0f));
        this.renderTop(matrixStack, buffer, te, frame, 2 + y, te.getWidth() - 1 - x, z, 0.2, te.getHeight() - 1, te.getColor());
        matrixStack.m_85845_(Vector3f.f_122226_.m_122240_(90.0f));
        matrixStack.m_85845_(Vector3f.f_122226_.m_122240_(90.0f));
        this.renderTop(matrixStack, buffer, te, frame, 2 - te.getHeight() + y, te.getWidth() + x, z, 0.0, te.getHeight() - 1, te.getColor());
        matrixStack.m_85845_(Vector3f.f_122226_.m_122240_(90.0f));
        this.renderTop(matrixStack, buffer, te, frame, -te.getWidth() - x + 1, -1 - y, z, 0.6, te.getWidth() * 2, te.getColor());
        matrixStack.m_85849_();
    }
}

