package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies;


import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ShootingBasketballEntityRenderer extends GeoProjectilesRenderer {

	public ShootingBasketballEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingBasketballEntityModel());
		this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
	}
}
