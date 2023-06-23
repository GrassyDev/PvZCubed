package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.plasmapea;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ShootingPlasmapeaEntityRenderer extends GeoProjectilesRenderer {

	public ShootingPlasmapeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingPlasmaPeaEntityModel());
		this.shadowRadius = 0.1F; //change 0.7 to the desired shadow size.
	}
	protected int getBlockLight(ShootingPlasmaPeaEntity plantEntity, BlockPos blockPos) {
		return 15;
	}
}
