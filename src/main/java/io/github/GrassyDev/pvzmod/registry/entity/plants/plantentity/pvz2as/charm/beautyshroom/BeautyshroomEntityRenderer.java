package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2as.charm.beautyshroom;


import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class BeautyshroomEntityRenderer extends GeoEntityRenderer<BeautyshroomEntity> {

    public BeautyshroomEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BeautyshroomEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(BeautyshroomEntity plantEntity, BlockPos blockPos) {
		return Math.min(super.getBlockLight(plantEntity, blockPos) + 6, 15);
	}

}
