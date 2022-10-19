package net.fabricmc.example.registry.plants.renderers;

import net.fabricmc.example.registry.plants.models.PotatomineEntityModel;
import net.fabricmc.example.registry.plants.plantentity.PotatomineEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class PotatomineEntityRenderer extends GeoEntityRenderer<PotatomineEntity> {

    public PotatomineEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new PotatomineEntityModel());
        this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
    }

}