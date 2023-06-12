package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.electropea;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class ElectropeaEntityRenderer extends GeoEntityRenderer<ElectropeaEntity> {

    public ElectropeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ElectropeaEntityModel());
        this.shadowRadius = 0.2F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(ElectropeaEntity plantEntity, BlockPos blockPos) {
		return 15;
	}
}
