package io.github.GrassyDev.pvzmod.registry.plants.renderers;

import io.github.GrassyDev.pvzmod.registry.plants.models.FumeshroomEntityModel;
import io.github.GrassyDev.pvzmod.registry.plants.plantentity.FumeshroomEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class FumeshroomEntityRenderer extends GeoEntityRenderer<FumeshroomEntity> {

    public FumeshroomEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new FumeshroomEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
