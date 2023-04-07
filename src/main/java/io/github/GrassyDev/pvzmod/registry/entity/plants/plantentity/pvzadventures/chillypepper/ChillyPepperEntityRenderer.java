package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.chillypepper;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class ChillyPepperEntityRenderer extends GeoEntityRenderer<ChillyPepperEntity> {

    public ChillyPepperEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ChillyPepperEntityModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(ChillyPepperEntity plantEntity, BlockPos blockPos) {
		return plantEntity.getFuseSpeed() > 0 ? 4 : 0;
	}

}
