package io.github.GrassyDev.pvzmod.registry.plants.rendererspackage io.github.GrassyDev.pvzmod.registry.plants.renderers;

import io.github.GrassyDev.pvzmod.registry.plants.models.ChomperEntityModel;
import net.fabricmc.example.registry.plants.plantentity.ChomperEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class ChomperEntityRenderer extends GeoEntityRenderer<ChomperEntity> {

    public ChomperEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ChomperEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
