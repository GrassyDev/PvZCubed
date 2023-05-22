package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.lostcity.goldleaf;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class GoldLeafEntityRenderer extends GeoEntityRenderer<GoldLeafEntity> {

    public GoldLeafEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new GoldLeafEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(GoldLeafEntity plantEntity, BlockPos blockPos) {
		return Math.min(super.getBlockLight(plantEntity, blockPos) + 6, 15);
	}

}
