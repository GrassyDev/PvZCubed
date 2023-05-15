package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.card;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ShootingCardEntityRenderer extends GeoProjectilesRenderer {

	public ShootingCardEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingCardEntityModel());
		this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
	}

}
