package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.boomerang;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ShootingBoomerangEntityRenderer extends GeoProjectilesRenderer {

	public ShootingBoomerangEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingBoomerangEntityModel());
		this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
	}

}
