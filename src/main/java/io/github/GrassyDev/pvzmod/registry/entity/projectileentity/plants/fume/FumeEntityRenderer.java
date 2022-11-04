package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.fume;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class FumeEntityRenderer extends GeoProjectilesRenderer {

	public FumeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new FumeEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}

}
