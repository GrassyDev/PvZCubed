package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.sunflowerseed;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SunflowerSeedEntityRenderer extends GeoEntityRenderer<SunflowerSeedEntity> {

    public SunflowerSeedEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SunflowerSeedEntityModel());
        this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(SunflowerSeedEntity plantEntity, BlockPos blockPos) {
		return 2;
	}

}
