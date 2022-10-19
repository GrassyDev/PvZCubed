package net.fabricmc.example.registry.plants.renderers;

import net.fabricmc.example.registry.plants.models.HypnoshroomEntityModel;
import net.fabricmc.example.registry.plants.plantentity.HypnoshroomEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class HypnoshroomEntityRenderer extends GeoEntityRenderer<HypnoshroomEntity> {

    public HypnoshroomEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new HypnoshroomEntityModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

}