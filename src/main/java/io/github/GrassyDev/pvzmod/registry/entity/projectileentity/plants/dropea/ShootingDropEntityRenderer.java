package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.dropea;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ShootingDropEntityRenderer extends GeoProjectilesRenderer {

	public ShootingDropEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingDropEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}

}
