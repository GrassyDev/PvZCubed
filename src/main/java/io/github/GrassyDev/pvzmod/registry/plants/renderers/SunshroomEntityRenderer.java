package net.fabricmc.example.registry.plants.renderers;

import net.fabricmc.example.registry.plants.models.PuffshroomEntityModel;
import net.fabricmc.example.registry.plants.models.SunshroomEntityModel;
import net.fabricmc.example.registry.plants.plantentity.PuffshroomEntity;
import net.fabricmc.example.registry.plants.plantentity.SunshroomEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SunshroomEntityRenderer extends GeoEntityRenderer<SunshroomEntity> {

    public SunshroomEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new SunshroomEntityModel());
        this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
    }

}