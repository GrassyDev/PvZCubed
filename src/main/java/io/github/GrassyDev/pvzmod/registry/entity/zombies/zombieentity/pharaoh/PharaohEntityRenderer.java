package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pharaoh;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class PharaohEntityRenderer extends GeoEntityRenderer<PharaohEntity> {

    public PharaohEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new PharaohEntityModel());
        this.shadowRadius = 0.9F; //change 0.7 to the desired shadow size.
    }
}
