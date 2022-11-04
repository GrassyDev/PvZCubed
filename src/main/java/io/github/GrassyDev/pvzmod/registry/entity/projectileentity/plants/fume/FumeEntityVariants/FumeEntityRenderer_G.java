package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.fume.FumeEntityVariants;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class FumeEntityRenderer_G extends GeoProjectilesRenderer {

	public FumeEntityRenderer_G(EntityRendererFactory.Context ctx) {
		super(ctx, new FumeEntityModel_G());
		this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
	}

}
