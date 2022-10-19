package io.github.GrassyDev.pvzmod.registry.plants.rendererspackage io.github.GrassyDev.pvzmod.registry.plants.renderers;

import io.github.GrassyDev.pvzmod.registry.plants.models.ThreepeaterEntityModel;
import net.fabricmc.example.registry.plants.plantentity.ThreepeaterEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class ThreepeaterEntityRenderer extends GeoEntityRenderer<ThreepeaterEntity> {

    public ThreepeaterEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ThreepeaterEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

}
