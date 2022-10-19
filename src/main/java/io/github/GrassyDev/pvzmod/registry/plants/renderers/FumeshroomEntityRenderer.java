package net.fabricmc.example.registry.plants.renderers;

import net.fabricmc.example.registry.plants.models.FumeshroomEntityModel;
import net.fabricmc.example.registry.plants.models.PuffshroomEntityModel;
import net.fabricmc.example.registry.plants.plantentity.FumeshroomEntity;
import net.fabricmc.example.registry.plants.plantentity.PuffshroomEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class FumeshroomEntityRenderer extends GeoEntityRenderer<FumeshroomEntity> {

    public FumeshroomEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new FumeshroomEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}