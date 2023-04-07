package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.torchwood;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class TorchwoodEntityRenderer extends GeoEntityRenderer<TorchwoodEntity> {

    public TorchwoodEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new TorchwoodEntityModel());
        this.shadowRadius = 0.75F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(TorchwoodEntity plantEntity, BlockPos blockPos) {
		return plantEntity.isWet()? 0 : 15;
	}
}
