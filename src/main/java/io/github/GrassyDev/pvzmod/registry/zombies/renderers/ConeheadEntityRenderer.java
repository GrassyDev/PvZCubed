package net.fabricmc.example.registry.zombies.renderers;

import net.fabricmc.example.registry.zombies.models.ConeheadEntityModel;
import net.fabricmc.example.registry.zombies.zombieentity.ConeheadEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class ConeheadEntityRenderer extends GeoEntityRenderer<ConeheadEntity> {

    public ConeheadEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new ConeheadEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}