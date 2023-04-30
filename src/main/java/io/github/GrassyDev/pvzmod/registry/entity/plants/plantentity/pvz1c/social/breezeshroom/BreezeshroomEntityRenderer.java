package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.social.breezeshroom;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class BreezeshroomEntityRenderer extends GeoEntityRenderer<BreezeshroomEntity> {

    public BreezeshroomEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BreezeshroomEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
