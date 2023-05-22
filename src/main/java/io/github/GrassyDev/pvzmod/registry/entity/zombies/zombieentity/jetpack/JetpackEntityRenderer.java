package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.jetpack;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.JetpackVariants;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class JetpackEntityRenderer extends GeoEntityRenderer<JetpackEntity> {

    public JetpackEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new JetpackEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

	public static final Map<JetpackVariants, Identifier> LOCATION_MODEL_BY_VARIANT =
			Util.make(Maps.newEnumMap(JetpackVariants.class), (map) -> {
				map.put(JetpackVariants.JETPACK,
				        new Identifier("pvzmod", "geo/jetpack.geo.json"));
				map.put(JetpackVariants.JETPACKHYPNO,
						new Identifier("pvzmod", "geo/jetpack.geo.json"));
				map.put(JetpackVariants.BLASTRONAUT,
						new Identifier("pvzmod", "geo/blastronaut.geo.json"));
				map.put(JetpackVariants.BLASTRONAUTHYPNO,
						new Identifier("pvzmod", "geo/blastronaut.geo.json"));
			});

	public Identifier getModelResource(JetpackEntity object) {
		return LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
	}

	protected int getBlockLight(JetpackEntity zombieEntity, BlockPos blockPos) {
		return Math.min(super.getBlockLight(zombieEntity, blockPos) + 7, 15);
	}


	@Override
	public void render(GeoModel model, JetpackEntity animatable, float partialTick, RenderLayer type, MatrixStack poseStack, @Nullable VertexConsumerProvider bufferSource, @Nullable VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (animatable.getHypno()) {
			super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, 255, packedOverlay, 1, 255, 1, alpha);
		}
		else if (animatable.fireSplashTicks > 0){
			super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, 1, 255, 255, alpha);
		}
		else if(animatable.isIced || animatable.isFrozen){
			super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, 255, 75, 1, alpha);
		}
		else if (animatable.isPoisoned){
			super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, 100, 255, 1, alpha);
		}
		else {
			super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}
	}
}
