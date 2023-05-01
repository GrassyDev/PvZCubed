package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.beespike;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ShootingPowerBeeSpikeEntityRenderer extends GeoProjectilesRenderer {

	public ShootingPowerBeeSpikeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingPowerBeeSpikeEntityModel());
		this.shadowRadius = 0.2F; //change 0.7 to the desired shadow size.
	}
	protected int getBlockLight(ShootingPowerBeeSpikeEntity plantEntity, BlockPos blockPos) {
		return 15;
	}
}
