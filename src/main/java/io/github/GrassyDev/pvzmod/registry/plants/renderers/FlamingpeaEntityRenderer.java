package net.fabricmc.example.registry.plants.renderers;

import net.fabricmc.example.registry.plants.models.FlamingpeaEntityModel;
import net.fabricmc.example.registry.plants.models.PeashooterEntityModel;
import net.fabricmc.example.registry.plants.plantentity.FlamingpeaEntity;
import net.fabricmc.example.registry.plants.plantentity.PeashooterEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class FlamingpeaEntityRenderer extends GeoEntityRenderer<FlamingpeaEntity> {

    public FlamingpeaEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new FlamingpeaEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

}