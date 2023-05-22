package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.farfuture.empeach;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class EMPeachEntityRenderer extends GeoEntityRenderer<EMPeachEntity> {

    public EMPeachEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new EMPeachEntityModel());
        this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(EMPeachEntity plantEntity, BlockPos blockPos) {
		return Math.min(super.getBlockLight(plantEntity, blockPos) + 4, 15);
	}

}
