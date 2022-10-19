package io.github.GrassyDev.pvzmod.registry.plants.rendererspackage io.github.GrassyDev.pvzmod.registry.plants.renderers;

import io.github.GrassyDev.pvzmod.registry.plants.models.HypnoshroomEntityModel;
import net.fabricmc.example.registry.plants.plantentity.HypnoshroomEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class HypnoshroomEntityRenderer extends GeoEntityRenderer<HypnoshroomEntity> {

    public HypnoshroomEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new HypnoshroomEntityModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

}
