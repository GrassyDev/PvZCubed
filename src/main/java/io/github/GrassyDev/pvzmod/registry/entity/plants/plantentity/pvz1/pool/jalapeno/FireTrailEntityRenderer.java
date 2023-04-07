package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.jalapeno;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class FireTrailEntityRenderer extends GeoEntityRenderer<FireTrailEntity> {

    public FireTrailEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new FireTrailEntityModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(FireTrailEntity plantEntity, BlockPos blockPos) {
		return 15;
	}

}
