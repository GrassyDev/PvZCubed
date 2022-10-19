package net.fabricmc.example.registry.plants.renderers;

import net.fabricmc.example.registry.plants.models.IceshroomEntityModel;
import net.fabricmc.example.registry.plants.plantentity.IceshroomEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class IceshroomEntityRenderer extends GeoEntityRenderer<IceshroomEntity> {

    public IceshroomEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new IceshroomEntityModel());
        this.shadowRadius = 0.6F; //change 0.7 to the desired shadow size.
    }

}