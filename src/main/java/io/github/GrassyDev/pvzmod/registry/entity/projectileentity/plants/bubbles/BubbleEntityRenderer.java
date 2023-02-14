package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.bubbles;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class BubbleEntityRenderer extends GeoProjectilesRenderer {

	public BubbleEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BubbleEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}

}
