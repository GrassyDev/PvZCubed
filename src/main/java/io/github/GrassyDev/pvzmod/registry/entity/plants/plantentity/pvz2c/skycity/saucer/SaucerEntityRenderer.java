package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.skycity.saucer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SaucerEntityRenderer extends GeoEntityRenderer<SaucerEntity> {

    public SaucerEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SaucerEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(SaucerEntity plantEntity, BlockPos blockPos) {
		return (plantEntity.attacking) ? 15 : Math.min(super.getBlockLight(plantEntity, blockPos) + 6, 15);
	}
}
