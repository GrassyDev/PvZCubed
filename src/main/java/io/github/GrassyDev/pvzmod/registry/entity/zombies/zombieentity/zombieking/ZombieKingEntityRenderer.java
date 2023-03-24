package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.zombieking;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class ZombieKingEntityRenderer extends GeoEntityRenderer<ZombieKingEntity> {

    public ZombieKingEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ZombieKingEntityModel());
        this.shadowRadius = 0.9F; //change 0.7 to the desired shadow size.
    }

}
