package io.github.GrassyDev.pvzmod.registry.plants.plantentity.peapod;

import io.github.GrassyDev.pvzmod.registry.plants.plantentity.repeater.RepeaterEntity;
import io.github.GrassyDev.pvzmod.registry.plants.plantentity.repeater.RepeaterEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class PeapodEntityRenderer extends GeoEntityRenderer<PeapodEntity> {

    public PeapodEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new PeapodEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

}
