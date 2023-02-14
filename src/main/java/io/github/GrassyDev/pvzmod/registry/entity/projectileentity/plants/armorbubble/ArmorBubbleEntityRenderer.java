package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.armorbubble;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ArmorBubbleEntityRenderer extends GeoProjectilesRenderer {

	public ArmorBubbleEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ArmorBubbleEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}

}
