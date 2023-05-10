package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.kabloom.zapricot;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class ZapricotEntityRenderer extends GeoEntityRenderer<ZapricotEntity> {

    public ZapricotEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ZapricotEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(ZapricotEntity plantEntity, BlockPos blockPos) {
		return 1;
	}

}
