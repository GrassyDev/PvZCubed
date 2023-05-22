package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pumpkinproj;


import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ShootingPumpkinEntityRenderer extends GeoProjectilesRenderer {

	public ShootingPumpkinEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingPumpkinEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
