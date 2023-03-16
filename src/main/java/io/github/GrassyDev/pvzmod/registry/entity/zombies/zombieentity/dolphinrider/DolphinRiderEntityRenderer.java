package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.dolphinrider;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class DolphinRiderEntityRenderer extends GeoEntityRenderer<DolphinRiderEntity> {

    public DolphinRiderEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new DolphinRiderEntityModel());
        this.shadowRadius = 0.85F; //change 0.7 to the desired shadow size.
    }

}
