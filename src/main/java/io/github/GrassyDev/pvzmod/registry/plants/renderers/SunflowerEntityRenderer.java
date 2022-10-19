package net.fabricmc.example.registry.plants.renderers;

import net.fabricmc.example.registry.plants.models.SunflowerEntityModel;
import net.fabricmc.example.registry.plants.plantentity.SunflowerEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SunflowerEntityRenderer extends GeoEntityRenderer<SunflowerEntity> {

    public SunflowerEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new SunflowerEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

}