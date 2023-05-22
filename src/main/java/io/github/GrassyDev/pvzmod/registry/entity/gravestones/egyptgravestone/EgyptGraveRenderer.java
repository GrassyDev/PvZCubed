package io.github.GrassyDev.pvzmod.registry.entity.gravestones.egyptgravestone;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class EgyptGraveRenderer extends GeoEntityRenderer<EgyptGraveEntity> {

    public EgyptGraveRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new EgyptGraveModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(EgyptGraveEntity graveEntity, BlockPos blockPos) {
		return Math.min(super.getBlockLight(graveEntity, blockPos) + 6, 15);
	}

}
