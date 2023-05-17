package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.piercingpea;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class FirePiercePeaEntityRenderer extends GeoProjectilesRenderer {

	public FirePiercePeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new FirePiercePeaEntityModel());
		this.shadowRadius = 0.2F; //change 0.7 to the desired shadow size.
	}

}
