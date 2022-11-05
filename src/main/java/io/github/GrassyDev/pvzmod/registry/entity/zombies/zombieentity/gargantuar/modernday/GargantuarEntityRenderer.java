package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class GargantuarEntityRenderer extends GeoEntityRenderer<GargantuarEntity> {

    public GargantuarEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new GargantuarEntityModel());
        this.shadowRadius = 3F; //change 0.7 to the desired shadow size.
    }

}
