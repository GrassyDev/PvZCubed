package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ShootingSnowPeaEntityRenderer extends GeoProjectilesRenderer {

	public ShootingSnowPeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingSnowPeaEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
