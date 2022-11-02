package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.renderers;

import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.HypnoFlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.models.HypnoFlagzombieEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class HypnoFlagzombieEntityRenderer extends GeoEntityRenderer<HypnoFlagzombieEntity> {

    public HypnoFlagzombieEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new HypnoFlagzombieEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
