package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.piercingpea;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class PiercePeaEntityRenderer extends GeoProjectilesRenderer {

	public PiercePeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new PiercePeaEntityModel());
		this.shadowRadius = 0.2F; //change 0.7 to the desired shadow size.
	}

	protected int getBlockLight(FirePiercePeaEntity plantEntity, BlockPos blockPos) {
		return 15;
	}
}
