package io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.locustswarm;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class LocustswarmEntityRenderer extends GeoProjectilesRenderer {

	public LocustswarmEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new LocustswarmEntityModel());
		this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
	}
}
