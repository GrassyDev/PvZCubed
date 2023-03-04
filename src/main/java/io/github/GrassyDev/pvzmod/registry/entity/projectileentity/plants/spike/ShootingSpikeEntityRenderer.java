package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spike;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ShootingSpikeEntityRenderer extends GeoProjectilesRenderer {

	public ShootingSpikeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingSpikeEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
