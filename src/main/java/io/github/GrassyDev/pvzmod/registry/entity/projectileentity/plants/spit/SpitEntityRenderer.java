package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spit;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class SpitEntityRenderer extends GeoProjectilesRenderer {

	public SpitEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SpitEntityModel());
		this.shadowRadius = 0.2F; //change 0.7 to the desired shadow size.
	}

}
