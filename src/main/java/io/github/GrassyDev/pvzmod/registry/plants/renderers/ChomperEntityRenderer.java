package net.fabricmc.example.registry.plants.renderers;

import net.fabricmc.example.registry.plants.models.ChomperEntityModel;
import net.fabricmc.example.registry.plants.models.PeashooterEntityModel;
import net.fabricmc.example.registry.plants.plantentity.ChomperEntity;
import net.fabricmc.example.registry.plants.plantentity.PeashooterEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class ChomperEntityRenderer extends GeoEntityRenderer<ChomperEntity> {

    public ChomperEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new ChomperEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}