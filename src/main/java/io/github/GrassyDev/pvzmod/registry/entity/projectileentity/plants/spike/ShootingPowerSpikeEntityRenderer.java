package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spike;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ShootingPowerSpikeEntityRenderer extends GeoProjectilesRenderer {

	public ShootingPowerSpikeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingPowerSpikeEntityModel());
		this.shadowRadius = 0.2F; //change 0.7 to the desired shadow size.
	}
	protected int getBlockLight(ShootingPowerSpikeEntity plantEntity, BlockPos blockPos) {
		return 15;
	}
}
