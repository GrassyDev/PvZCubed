package net.fabricmc.example.registry.plants.renderers;

import net.fabricmc.example.registry.plants.models.RepeaterEntityModel;
import net.fabricmc.example.registry.plants.plantentity.RepeaterEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class RepeaterEntityRenderer extends GeoEntityRenderer<RepeaterEntity> {

    public RepeaterEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new RepeaterEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

}