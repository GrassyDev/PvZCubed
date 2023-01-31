package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.tanglekelp;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class TangleKelpEntityRenderer extends GeoEntityRenderer<TangleKelpEntity> {

    public TangleKelpEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new TangleKelpEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

}
