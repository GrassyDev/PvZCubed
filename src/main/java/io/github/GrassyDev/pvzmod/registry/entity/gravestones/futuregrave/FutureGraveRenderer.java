package io.github.GrassyDev.pvzmod.registry.entity.gravestones.futuregrave;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class FutureGraveRenderer extends GeoEntityRenderer<FutureGraveEntity> {

    public FutureGraveRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new FutureGraveModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(FutureGraveEntity graveEntity, BlockPos blockPos) {
		return 6;
	}

}
