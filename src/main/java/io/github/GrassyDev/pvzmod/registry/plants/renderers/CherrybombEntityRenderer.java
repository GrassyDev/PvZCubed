package net.fabricmc.example.registry.plants.renderers;

import net.fabricmc.example.registry.plants.models.CherrybombEntityModel;
import net.fabricmc.example.registry.plants.models.PeashooterEntityModel;
import net.fabricmc.example.registry.plants.plantentity.CherrybombEntity;
import net.fabricmc.example.registry.plants.plantentity.PeashooterEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class CherrybombEntityRenderer extends GeoEntityRenderer<CherrybombEntity> {

    public CherrybombEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new CherrybombEntityModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

}