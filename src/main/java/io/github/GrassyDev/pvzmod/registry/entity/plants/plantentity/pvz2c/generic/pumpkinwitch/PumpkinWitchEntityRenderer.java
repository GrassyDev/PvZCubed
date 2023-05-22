package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.pumpkinwitch;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class PumpkinWitchEntityRenderer extends GeoEntityRenderer<PumpkinWitchEntity> {

    public PumpkinWitchEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new PumpkinWitchEntityModel());
        this.shadowRadius = 0.8F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(PumpkinWitchEntity plantEntity, BlockPos blockPos) {
		return Math.min(super.getBlockLight(plantEntity, blockPos) + 13, 15);
	}

}
