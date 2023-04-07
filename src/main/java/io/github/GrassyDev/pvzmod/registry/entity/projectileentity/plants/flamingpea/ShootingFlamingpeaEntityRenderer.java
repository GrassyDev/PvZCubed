package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.flamingpea;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ShootingFlamingpeaEntityRenderer extends GeoProjectilesRenderer {

	public ShootingFlamingpeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingFlamingPeaEntityModel());
		this.shadowRadius = 0.1F; //change 0.7 to the desired shadow size.
	}

	protected int getBlockLight(ShootingFlamingPeaEntity plantEntity, BlockPos blockPos) {
		return 15;
	}
}
