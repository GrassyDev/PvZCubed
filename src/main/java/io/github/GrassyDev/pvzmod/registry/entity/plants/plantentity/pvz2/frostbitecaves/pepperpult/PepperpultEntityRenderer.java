package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.frostbitecaves.pepperpult;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class PepperpultEntityRenderer extends GeoEntityRenderer<PepperpultEntity> {

    public PepperpultEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new PepperpultEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
