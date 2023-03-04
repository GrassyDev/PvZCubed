package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.dandelionweed;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class DandelionWeedEntityRenderer extends GeoEntityRenderer<DandelionWeedEntity> {

    public DandelionWeedEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new DandelionWeedEntityModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

}
