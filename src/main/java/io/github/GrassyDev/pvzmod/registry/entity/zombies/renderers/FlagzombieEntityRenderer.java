package io.github.GrassyDev.pvzmod.registry.entity.zombies.renderers;

import io.github.GrassyDev.pvzmod.registry.entity.zombies.models.FlagzombieEntityModel;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.FlagzombieEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class FlagzombieEntityRenderer extends GeoEntityRenderer<FlagzombieEntity> {

    public FlagzombieEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new FlagzombieEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
