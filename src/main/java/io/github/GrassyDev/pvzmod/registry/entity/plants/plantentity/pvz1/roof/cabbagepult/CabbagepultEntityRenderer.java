package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.roof.cabbagepult;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class CabbagepultEntityRenderer extends GeoEntityRenderer<CabbagepultEntity> {

    public CabbagepultEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new CabbagepultEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
