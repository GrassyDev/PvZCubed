package io.github.GrassyDev.pvzmod.registry.entity.environment.solarwinds;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SolarWindsTileRenderer extends GeoEntityRenderer<SolarWinds> {

    public SolarWindsTileRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SolarWindsModel());
        this.shadowRadius = 1F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(SolarWinds tile, BlockPos blockPos) {
		return 15;
	}

}
