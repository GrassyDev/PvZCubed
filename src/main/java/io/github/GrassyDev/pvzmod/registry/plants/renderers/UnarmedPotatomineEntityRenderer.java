package net.fabricmc.example.registry.plants.renderers;

import net.fabricmc.example.registry.plants.models.PotatomineEntityModel;
import net.fabricmc.example.registry.plants.models.UnarmedPotatomineEntityModel;
import net.fabricmc.example.registry.plants.plantentity.PotatomineEntity;
import net.fabricmc.example.registry.plants.plantentity.UnarmedPotatomineEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class UnarmedPotatomineEntityRenderer extends GeoEntityRenderer<UnarmedPotatomineEntity> {

    public UnarmedPotatomineEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new UnarmedPotatomineEntityModel());
        this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
    }

}