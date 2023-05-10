package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.groundbounce;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class GroundBounceEntityRenderer extends GeoProjectilesRenderer {

	public GroundBounceEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new GroundBounceEntityModel());
		this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
	}

}
