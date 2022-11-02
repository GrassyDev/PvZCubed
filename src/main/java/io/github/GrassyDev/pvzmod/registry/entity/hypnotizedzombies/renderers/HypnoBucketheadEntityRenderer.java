package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.renderers;


import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.HypnoBucketheadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.models.HypnoBucketheadEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class HypnoBucketheadEntityRenderer extends GeoEntityRenderer<HypnoBucketheadEntity> {

    public HypnoBucketheadEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new HypnoBucketheadEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
