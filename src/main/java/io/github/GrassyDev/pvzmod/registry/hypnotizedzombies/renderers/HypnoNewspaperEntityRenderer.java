package net.fabricmc.example.registry.hypnotizedzombies.renderers;

import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoBrowncoatEntity;
import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoNewspaperEntity;
import net.fabricmc.example.registry.hypnotizedzombies.models.HypnoBrowncoatEntityModel;
import net.fabricmc.example.registry.hypnotizedzombies.models.HypnoNewspaperEntityModel;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class HypnoNewspaperEntityRenderer extends GeoEntityRenderer<HypnoNewspaperEntity> {

    public HypnoNewspaperEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new HypnoNewspaperEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}