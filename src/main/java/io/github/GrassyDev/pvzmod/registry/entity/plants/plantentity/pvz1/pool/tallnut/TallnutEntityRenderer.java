package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.tallnut;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class TallnutEntityRenderer extends GeoEntityRenderer<TallnutEntity> {

    public TallnutEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new TallnutEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
