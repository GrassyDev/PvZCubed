package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.scrapped.icebergpult;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class IcebergpultEntityRenderer extends GeoEntityRenderer<IcebergpultEntity> {

    public IcebergpultEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new IcebergpultEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
