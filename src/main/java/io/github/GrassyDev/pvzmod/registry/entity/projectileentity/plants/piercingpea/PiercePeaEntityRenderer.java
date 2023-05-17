package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.piercingpea;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class PiercePeaEntityRenderer extends GeoProjectilesRenderer {

	public PiercePeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new PiercePeaEntityModel());
		this.shadowRadius = 0.2F; //change 0.7 to the desired shadow size.
	}

}
