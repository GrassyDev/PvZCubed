package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.screendoor;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class ScreendoorShieldEntityRenderer extends GeoEntityRenderer<ScreendoorShieldEntity> {

    public ScreendoorShieldEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ScreendoorShieldEntityModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }

}
