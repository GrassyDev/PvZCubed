package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.peashooter;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class PeashooterEntityRenderer extends GeoEntityRenderer<PeashooterEntity> {

    public PeashooterEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new PeashooterEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

}
