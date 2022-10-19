package net.fabricmc.example.registry.zombies.renderers;

import net.fabricmc.example.registry.zombies.models.BrowncoatEntityModel;
import net.fabricmc.example.registry.zombies.zombieentity.BrowncoatEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class BrowncoatEntityRenderer extends GeoEntityRenderer<BrowncoatEntity> {

    public BrowncoatEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new BrowncoatEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}