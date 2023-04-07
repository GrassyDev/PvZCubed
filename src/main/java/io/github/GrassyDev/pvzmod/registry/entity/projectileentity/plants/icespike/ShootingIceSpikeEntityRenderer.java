package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.icespike;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ShootingIceSpikeEntityRenderer extends GeoProjectilesRenderer {

	public ShootingIceSpikeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingIcespikeEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
	protected int getBlockLight(ShootingIcespikeEntity plantEntity, BlockPos blockPos) {
		return 1;
	}
}
