/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.reward.storage.ClientRewardStorage
 *  com.hrznstudio.titanium.reward.storage.EnabledRewards
 *  com.mojang.blaze3d.platform.GlStateManager$DestFactor
 *  com.mojang.blaze3d.platform.GlStateManager$SourceFactor
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexFormat
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  net.minecraft.client.model.EntityModel
 *  net.minecraft.client.model.PlayerModel
 *  net.minecraft.client.player.AbstractClientPlayer
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderStateShard$CullStateShard
 *  net.minecraft.client.renderer.RenderStateShard$EmptyTextureStateShard
 *  net.minecraft.client.renderer.RenderStateShard$OffsetTexturingStateShard
 *  net.minecraft.client.renderer.RenderStateShard$OverlayStateShard
 *  net.minecraft.client.renderer.RenderStateShard$ShaderStateShard
 *  net.minecraft.client.renderer.RenderStateShard$TextureStateShard
 *  net.minecraft.client.renderer.RenderStateShard$TexturingStateShard
 *  net.minecraft.client.renderer.RenderStateShard$TransparencyStateShard
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.RenderType$CompositeState
 *  net.minecraft.client.renderer.entity.LivingEntityRenderer
 *  net.minecraft.client.renderer.entity.layers.RenderLayer
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 */
package com.buuz135.portality.proxy.client.render;

import com.buuz135.portality.Portality;
import com.hrznstudio.titanium.reward.storage.ClientRewardStorage;
import com.hrznstudio.titanium.reward.storage.EnabledRewards;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class AuraRender
extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    public static PlayerModel model;

    public AuraRender(LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer) {
        super(renderer);
        model = (PlayerModel)renderer.m_7200_();
    }

    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, AbstractClientPlayer entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!ClientRewardStorage.REWARD_STORAGE.getRewards().containsKey(entityIn.m_142081_())) {
            return;
        }
        if (!((EnabledRewards)ClientRewardStorage.REWARD_STORAGE.getRewards().get(entityIn.m_142081_())).getEnabled().containsKey(new ResourceLocation("portality", "aura"))) {
            return;
        }
        Portality.AuraType type = Portality.AuraType.valueOf((String)((EnabledRewards)ClientRewardStorage.REWARD_STORAGE.getRewards().get(entityIn.m_142081_())).getEnabled().get(new ResourceLocation("portality", "aura")));
        float f = (float)entityIn.f_19797_ + partialTicks;
        EntityModel entitymodel = this.m_117386_();
        entitymodel.m_6839_((Entity)entityIn, limbSwing, limbSwingAmount, partialTicks);
        ((PlayerModel)this.m_117386_()).m_102624_(entitymodel);
        RenderType.CompositeState rendertype$state = RenderType.CompositeState.m_110628_().m_173292_(new RenderStateShard.ShaderStateShard(GameRenderer::m_172703_)).m_173290_((RenderStateShard.EmptyTextureStateShard)new RenderStateShard.TextureStateShard(type.getResourceLocation(), false, false)).m_110685_(new RenderStateShard.TransparencyStateShard("translucent_transparency", () -> {
            RenderSystem.m_69478_();
            if (type.isEnableBlend()) {
                RenderSystem.m_69408_((GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE);
            }
        }, () -> {
            RenderSystem.m_69461_();
            RenderSystem.m_69453_();
        })).m_110677_(new RenderStateShard.OverlayStateShard(true)).m_110661_(new RenderStateShard.CullStateShard(false)).m_110683_((RenderStateShard.TexturingStateShard)new RenderStateShard.OffsetTexturingStateShard(0.0f, f * 0.01f)).m_110691_(true);
        entitymodel.m_6973_((Entity)entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        entitymodel.m_7695_(matrixStackIn, bufferIn.m_6299_((RenderType)RenderType.m_173215_((String)"portality_aura", (VertexFormat)DefaultVertexFormat.f_85812_, (VertexFormat.Mode)VertexFormat.Mode.QUADS, (int)256, (boolean)true, (boolean)false, (RenderType.CompositeState)rendertype$state)), 100, 100, 0.5f, 0.5f, 0.5f, 0.5f);
    }
}

