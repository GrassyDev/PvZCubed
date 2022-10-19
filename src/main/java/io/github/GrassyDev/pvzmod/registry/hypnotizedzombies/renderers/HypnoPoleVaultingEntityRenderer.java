package io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.renderers;

import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoPoleVaultingEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.models.HypnoPoleVaultingEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class HypnoPoleVaultingEntityRenderer extends GeoEntityRenderer<HypnoPoleVaultingEntity> {

    public HypnoPoleVaultingEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new HypnoPoleVaultingEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
