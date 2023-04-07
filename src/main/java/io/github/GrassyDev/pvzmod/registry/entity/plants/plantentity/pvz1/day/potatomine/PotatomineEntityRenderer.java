package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.potatomine;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class PotatomineEntityRenderer extends GeoEntityRenderer<PotatomineEntity> {

    public PotatomineEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new PotatomineEntityModel());
        this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(PotatomineEntity plantEntity, BlockPos blockPos) {
		return plantEntity.getPotatoStage()? 8 : 0;
	}

}
