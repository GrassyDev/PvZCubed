package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.weeniebeanie;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class WeenieBeanieEntityRenderer extends GeoEntityRenderer<WeenieBeanieEntity> {

    public WeenieBeanieEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new WeenieBeanieEntityModel());
        this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
    }

}
