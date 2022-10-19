package io.github.GrassyDev.pvzmod.registry.plants.rendererspackage io.github.GrassyDev.pvzmod.registry.plants.renderers;

import net.fabricmc.example.registry.plants.models.CherrybombEntityModel;
import net.fabricmc.example.registry.plants.models.DoomshroomEntityModel;
import net.fabricmc.example.registry.plants.plantentity.CherrybombEntity;
import net.fabricmc.example.registry.plants.plantentity.DoomshroomEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class DoomshroomEntityRenderer extends GeoEntityRenderer<DoomshroomEntity> {

    public DoomshroomEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new DoomshroomEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
