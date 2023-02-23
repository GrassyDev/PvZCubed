package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.coconut;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class CoconutEntityRenderer extends GeoProjectilesRenderer {

	public CoconutEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new CoconutEntityModel());
		this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
	}
}
