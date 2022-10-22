package io.github.GrassyDev.pvzmod.registry.plants.projectileentity.FumeEntityVariants;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class FumeEntityRenderer_T extends GeoProjectilesRenderer {

	public FumeEntityRenderer_T(EntityRendererFactory.Context ctx) {
		super(ctx, new FumeEntityModel_T());
		this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
	}

}
