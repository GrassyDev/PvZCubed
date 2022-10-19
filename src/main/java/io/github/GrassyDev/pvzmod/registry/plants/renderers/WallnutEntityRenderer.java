package io.github.GrassyDev.pvzmod.registry.plants.renderers;

import io.github.GrassyDev.pvzmod.registry.plants.models.WallnutEntityModel;
import io.github.GrassyDev.pvzmod.registry.plants.plantentity.WallnutEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class WallnutEntityRenderer extends GeoEntityRenderer<WallnutEntity> {

    public WallnutEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new WallnutEntityModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

}
