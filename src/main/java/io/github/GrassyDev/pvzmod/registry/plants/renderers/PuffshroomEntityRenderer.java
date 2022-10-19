package io.github.GrassyDev.pvzmod.registry.plants.rendererspackage io.github.GrassyDev.pvzmod.registry.plants.renderers;

import io.github.GrassyDev.pvzmod.registry.plants.models.PuffshroomEntityModel;
import net.fabricmc.example.registry.plants.plantentity.PuffshroomEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class PuffshroomEntityRenderer extends GeoEntityRenderer<PuffshroomEntity> {

    public PuffshroomEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new PuffshroomEntityModel());
        this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
    }

}
