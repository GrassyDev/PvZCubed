package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.lostcity.goldleaf;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class GoldLeafEntityRenderer extends GeoEntityRenderer<GoldLeafEntity> {

    public GoldLeafEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new GoldLeafEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

}
