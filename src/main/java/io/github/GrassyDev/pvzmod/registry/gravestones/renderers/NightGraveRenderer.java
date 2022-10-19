package net.fabricmc.example.registry.gravestones.renderers;

import net.fabricmc.example.registry.gravestones.gravestoneentity.BasicGraveEntity;
import net.fabricmc.example.registry.gravestones.gravestoneentity.NightGraveEntity;
import net.fabricmc.example.registry.gravestones.models.BasicGraveModel;
import net.fabricmc.example.registry.gravestones.models.NightGraveModel;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class NightGraveRenderer extends GeoEntityRenderer<NightGraveEntity> {

    public NightGraveRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new NightGraveModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

}