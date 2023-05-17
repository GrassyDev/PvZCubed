package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.retrogatling;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class RetroGatlingEntityRenderer extends GeoEntityRenderer<RetroGatlingEntity> {

    public RetroGatlingEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new RetroGatlingEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

}
