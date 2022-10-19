package net.fabricmc.example.registry.zombies.renderers;

import net.fabricmc.example.registry.zombies.models.NewspaperEntityModel;
import net.fabricmc.example.registry.zombies.zombieentity.NewspaperEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class NewspaperEntityRenderer extends GeoEntityRenderer<NewspaperEntity> {

    public NewspaperEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new NewspaperEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}