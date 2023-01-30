package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SnorkelEntityRenderer extends GeoEntityRenderer<SnorkelEntity> {

    public SnorkelEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SnorkelEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
