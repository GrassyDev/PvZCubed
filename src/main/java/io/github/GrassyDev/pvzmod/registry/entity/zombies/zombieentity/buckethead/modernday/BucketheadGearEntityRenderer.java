package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.buckethead.modernday;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class BucketheadGearEntityRenderer extends GeoEntityRenderer<BucketheadGearEntity> {

    public BucketheadGearEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BucketheadGearEntityModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }

}
