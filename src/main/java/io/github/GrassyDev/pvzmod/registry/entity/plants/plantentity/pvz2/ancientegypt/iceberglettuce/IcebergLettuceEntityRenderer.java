package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.ancientegypt.iceberglettuce;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class IcebergLettuceEntityRenderer extends GeoEntityRenderer<IcebergLettuceEntity> {

    public IcebergLettuceEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new IcebergLettuceEntityModel());
        this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(IcebergLettuceEntity plantEntity, BlockPos blockPos) {
		return Math.min(super.getBlockLight(plantEntity, blockPos) + 8, 15);
	}

}
