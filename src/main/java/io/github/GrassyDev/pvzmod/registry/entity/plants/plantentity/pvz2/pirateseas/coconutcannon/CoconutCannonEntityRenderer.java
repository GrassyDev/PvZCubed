package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.pirateseas.coconutcannon;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class CoconutCannonEntityRenderer extends GeoEntityRenderer<CoconutCannonEntity> {

    public CoconutCannonEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new CoconutCannonEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(CoconutCannonEntity plantEntity, BlockPos blockPos) {
		return (plantEntity.isFiring) ? Math.min(super.getBlockLight(plantEntity, blockPos) + 7, 15) : 0;
	}

}
