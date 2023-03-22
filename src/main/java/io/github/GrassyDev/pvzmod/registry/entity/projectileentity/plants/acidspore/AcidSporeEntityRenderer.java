package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.acidspore;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class AcidSporeEntityRenderer extends GeoProjectilesRenderer {

	public AcidSporeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new AcidSporeEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
		this.widthScale = 0.25F;
		this.heightScale = 0.25F;
	}

}
