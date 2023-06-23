package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.flamingpea;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class FlamingpeaEntityRenderer extends GeoEntityRenderer<FlamingpeaEntity> {

    public FlamingpeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new FlamingpeaEntityModel());
        this.shadowRadius = 0.2F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(FlamingpeaEntity plantEntity, BlockPos blockPos) {
		return plantEntity.isWet()? 0 : 15;
	}
}
