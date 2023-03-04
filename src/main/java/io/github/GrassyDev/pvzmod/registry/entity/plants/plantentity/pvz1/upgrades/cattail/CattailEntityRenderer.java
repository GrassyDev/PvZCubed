package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.cattail;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class CattailEntityRenderer extends GeoEntityRenderer<CattailEntity> {

    public CattailEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new CattailEntityModel());
        this.shadowRadius = 0.6F; //change 0.7 to the desired shadow size.
    }

}
