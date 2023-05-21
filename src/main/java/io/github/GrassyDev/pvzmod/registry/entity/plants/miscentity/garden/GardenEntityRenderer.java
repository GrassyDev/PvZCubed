package io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.garden;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class GardenEntityRenderer extends GeoEntityRenderer<GardenEntity> {

    public GardenEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new GardenEntityModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }

}
