package io.github.GrassyDev.pvzmod.registry.entity.gravestones.roofgrave;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class RoofGraveRenderer extends GeoEntityRenderer<RoofGraveEntity> {

    public RoofGraveRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new RoofGraveModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(RoofGraveEntity graveEntity, BlockPos blockPos) {
		return 6;
	}

}
