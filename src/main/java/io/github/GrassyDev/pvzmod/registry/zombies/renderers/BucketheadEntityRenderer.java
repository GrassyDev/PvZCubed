package net.fabricmc.example.registry.zombies.renderers;

import net.fabricmc.example.registry.zombies.models.BucketheadEntityModel;
import net.fabricmc.example.registry.zombies.zombieentity.BucketheadEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class BucketheadEntityRenderer extends GeoEntityRenderer<BucketheadEntity> {

    public BucketheadEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new BucketheadEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}