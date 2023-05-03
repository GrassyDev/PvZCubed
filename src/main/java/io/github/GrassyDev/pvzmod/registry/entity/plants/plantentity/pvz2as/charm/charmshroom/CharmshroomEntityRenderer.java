package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2as.charm.charmshroom;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class CharmshroomEntityRenderer extends GeoEntityRenderer<CharmshroomEntity> {

    public CharmshroomEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new CharmshroomEntityModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(CharmshroomEntity plantEntity, BlockPos blockPos) {
		return 11;
	}

}
