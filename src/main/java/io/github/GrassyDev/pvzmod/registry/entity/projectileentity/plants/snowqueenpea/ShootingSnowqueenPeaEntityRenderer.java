package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.snowqueenpea;

import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.snowpea.ShootingSnowPeaEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ShootingSnowqueenPeaEntityRenderer extends GeoProjectilesRenderer {

	public ShootingSnowqueenPeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingSnowqueenPeaEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
