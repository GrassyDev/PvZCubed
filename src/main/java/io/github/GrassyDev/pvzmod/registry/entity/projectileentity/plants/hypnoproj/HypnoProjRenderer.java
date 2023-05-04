package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.hypnoproj;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class HypnoProjRenderer extends GeoProjectilesRenderer {

	public HypnoProjRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new HypnoProjModel());
		this.shadowRadius = 0.1F; //change 0.7 to the desired shadow size.
	}
}
