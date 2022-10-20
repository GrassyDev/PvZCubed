package io.github.GrassyDev.pvzmod.registry.plants.projectileentity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ShootingPeaEntityRenderer extends GeoProjectilesRenderer {

	public ShootingPeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingPeaEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}

}
