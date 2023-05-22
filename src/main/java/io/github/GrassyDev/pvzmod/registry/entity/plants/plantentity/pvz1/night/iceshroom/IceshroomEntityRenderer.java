package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.iceshroom;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class IceshroomEntityRenderer extends GeoEntityRenderer<IceshroomEntity> {

    public IceshroomEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new IceshroomEntityModel());
        this.shadowRadius = 0.6F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(IceshroomEntity plantEntity, BlockPos blockPos) {
		return Math.min(super.getBlockLight(plantEntity, blockPos) + 3, 15);
	}

}
