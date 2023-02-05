package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.cabbage;


import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ShootingCabbageEntityRenderer extends GeoProjectilesRenderer {

	public ShootingCabbageEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingCabbageEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
