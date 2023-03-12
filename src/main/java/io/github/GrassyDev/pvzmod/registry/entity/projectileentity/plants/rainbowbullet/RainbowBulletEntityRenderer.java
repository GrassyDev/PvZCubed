package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.rainbowbullet;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class RainbowBulletEntityRenderer extends GeoProjectilesRenderer {

	public RainbowBulletEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new RainbowBulletEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
