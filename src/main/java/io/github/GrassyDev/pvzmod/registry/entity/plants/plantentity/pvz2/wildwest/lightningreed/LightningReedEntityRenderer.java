package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.wildwest.lightningreed;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class LightningReedEntityRenderer extends GeoEntityRenderer<LightningReedEntity> {

    public LightningReedEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new LightningReedEntityModel());
        this.shadowRadius = 0.25F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(LightningReedEntity plantEntity, BlockPos blockPos) {
		return 15;
	}

}
