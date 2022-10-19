package net.fabricmc.example.registry.plants.renderers;

import net.fabricmc.example.registry.plants.models.SunflowerEntityModel;
import net.fabricmc.example.registry.plants.models.WallnutEntityModel;
import net.fabricmc.example.registry.plants.plantentity.SunflowerEntity;
import net.fabricmc.example.registry.plants.plantentity.WallnutEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class WallnutEntityRenderer extends GeoEntityRenderer<WallnutEntity> {

    public WallnutEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new WallnutEntityModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

}