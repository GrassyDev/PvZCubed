package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.screendoor;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class HypnoScreendoorEntityRenderer extends GeoEntityRenderer<HypnoScreendoorEntity> {

    public HypnoScreendoorEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new HypnoScreendoorEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
