package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.iceberg;


import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ShootingIcebergEntityRenderer extends GeoProjectilesRenderer {

	public ShootingIcebergEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingIcebergEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
