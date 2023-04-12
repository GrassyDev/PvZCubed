package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.dropea;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class DropeaEntityRenderer extends GeoEntityRenderer<DropeaEntity> {


    public DropeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new DropeaEntityModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

}
