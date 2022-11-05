package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.buckethead.modernday;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class BucketheadEntityRenderer extends GeoEntityRenderer<BucketheadEntity> {

    public BucketheadEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BucketheadEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
