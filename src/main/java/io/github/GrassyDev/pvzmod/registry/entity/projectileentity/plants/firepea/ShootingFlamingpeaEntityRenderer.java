package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.firepea;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ShootingFlamingpeaEntityRenderer extends GeoProjectilesRenderer {

	public ShootingFlamingpeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingFlamingPeaEntityModel());
		this.shadowRadius = 0.1F; //change 0.7 to the desired shadow size.
	}
}
