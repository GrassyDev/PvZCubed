package io.github.GrassyDev.pvzmod.registry.plants.plantentity.sunshroom;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SunshroomEntityRenderer extends GeoEntityRenderer<SunshroomEntity> {

    public SunshroomEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SunshroomEntityModel());
        this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
    }

}
