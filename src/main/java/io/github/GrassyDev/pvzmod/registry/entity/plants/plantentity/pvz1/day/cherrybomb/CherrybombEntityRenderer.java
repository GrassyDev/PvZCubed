package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.cherrybomb;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class CherrybombEntityRenderer extends GeoEntityRenderer<CherrybombEntity> {

    public CherrybombEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new CherrybombEntityModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(CherrybombEntity plantEntity, BlockPos blockPos) {
		return plantEntity.getFuseSpeed() > 0 ? 8 : 0;
	}

}
