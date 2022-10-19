package net.fabricmc.example.registry.gravestones.renderers;

import net.fabricmc.example.registry.gravestones.gravestoneentity.BasicGraveEntity;
import net.fabricmc.example.registry.gravestones.models.BasicGraveModel;
import net.fabricmc.example.registry.zombies.models.BrowncoatEntityModel;
import net.fabricmc.example.registry.zombies.zombieentity.BrowncoatEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class BasicGraveRenderer extends GeoEntityRenderer<BasicGraveEntity> {

    public BasicGraveRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new BasicGraveModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

}