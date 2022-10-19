package net.fabricmc.example.registry.plants.renderers;

import net.fabricmc.example.registry.plants.models.GravebusterEntityModel;
import net.fabricmc.example.registry.plants.plantentity.GravebusterEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class GravebusterEntityRenderer extends GeoEntityRenderer<GravebusterEntity> {

    public GravebusterEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new GravebusterEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}