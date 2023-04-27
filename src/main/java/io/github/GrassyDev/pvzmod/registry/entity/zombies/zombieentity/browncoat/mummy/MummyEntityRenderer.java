package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.mummy;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.BrowncoatVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.darkages.PeasantEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class MummyEntityRenderer extends GeoEntityRenderer<MummyEntity> {

    public MummyEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new MummyEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

	public static final Map<BrowncoatVariants, Identifier> LOCATION_MODEL_BY_VARIANT =
			Util.make(Maps.newEnumMap(BrowncoatVariants.class), (map) -> {
				map.put(BrowncoatVariants.BROWNCOAT,
				        new Identifier("pvzmod", "geo/mummy.geo.json"));
				map.put(BrowncoatVariants.BROWNCOATHYPNO,
						new Identifier("pvzmod", "geo/mummy.geo.json"));
				map.put(BrowncoatVariants.CONEHEAD,
						new Identifier("pvzmod", "geo/mummycone.geo.json"));
				map.put(BrowncoatVariants.CONEHEADHYPNO,
						new Identifier("pvzmod", "geo/mummycone.geo.json"));
				map.put(BrowncoatVariants.BUCKETHEAD,
						new Identifier("pvzmod", "geo/mummybucket.geo.json"));
				map.put(BrowncoatVariants.BUCKETHEADHYPNO,
						new Identifier("pvzmod", "geo/mummybucket.geo.json"));
				map.put(BrowncoatVariants.PYRAMIDHEAD,
						new Identifier("pvzmod", "geo/pyramidhead.geo.json"));
				map.put(BrowncoatVariants.PYRAMIDHEADHYPNO,
						new Identifier("pvzmod", "geo/pyramidhead.geo.json"));
			});

	public Identifier getModelResource(PeasantEntity object) {
		return LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
	}

	@Override
	public void render(GeoModel model, MummyEntity animatable, float partialTick, RenderLayer type, MatrixStack poseStack, @Nullable VertexConsumerProvider bufferSource, @Nullable VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
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
