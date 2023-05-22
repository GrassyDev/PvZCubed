package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.puffshroom;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class PuffshroomEntityRenderer extends GeoEntityRenderer<PuffshroomEntity> {

    public PuffshroomEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new PuffshroomEntityModel());
        this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(PuffshroomEntity plantEntity, BlockPos blockPos) {
		return Math.min(super.getBlockLight(plantEntity, blockPos) + 4, 15);
	}

}
