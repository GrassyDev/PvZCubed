package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.electricpea;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ShootingElectricpeaEntityRenderer extends GeoProjectilesRenderer {

	public ShootingElectricpeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingElectricPeaEntityModel());
		this.shadowRadius = 0.1F; //change 0.7 to the desired shadow size.
	}
	protected int getBlockLight(ShootingElectricPeaEntity plantEntity, BlockPos blockPos) {
		return 15;
	}
}
