package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.acidshroom;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class AcidshroomEntityRenderer extends GeoEntityRenderer<AcidshroomEntity> {

    public AcidshroomEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new AcidshroomEntityModel());
        this.shadowRadius = 0.85F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(AcidshroomEntity plantEntity, BlockPos blockPos) {
		return 3;
	}

}
