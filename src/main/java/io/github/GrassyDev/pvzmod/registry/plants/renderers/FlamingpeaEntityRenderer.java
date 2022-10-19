package io.github.GrassyDev.pvzmod.registry.plants.rendererspackage io.github.GrassyDev.pvzmod.registry.plants.renderers;

import io.github.GrassyDev.pvzmod.registry.plants.models.FlamingpeaEntityModel;
import net.fabricmc.example.registry.plants.plantentity.FlamingpeaEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class FlamingpeaEntityRenderer extends GeoEntityRenderer<FlamingpeaEntity> {

    public FlamingpeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new FlamingpeaEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

}
