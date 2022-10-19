package io.github.GrassyDev.pvzmod.registry.plants.renderers;

import io.github.GrassyDev.pvzmod.registry.plants.models.SnowpeaEntityModel;
import io.github.GrassyDev.pvzmod.registry.plants.plantentity.SnowpeaEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SnowpeaEntityRenderer extends GeoEntityRenderer<SnowpeaEntity> {

    public SnowpeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SnowpeaEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

}
