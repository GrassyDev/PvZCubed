package net.fabricmc.example.registry.hypnotizedzombies.renderers;

import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoBrowncoatEntity;
import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoFlagzombieEntity;
import net.fabricmc.example.registry.hypnotizedzombies.models.HypnoBrowncoatEntityModel;
import net.fabricmc.example.registry.hypnotizedzombies.models.HypnoFlagzombieEntityModel;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class HypnoFlagzombieEntityRenderer extends GeoEntityRenderer<HypnoFlagzombieEntity> {

    public HypnoFlagzombieEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new HypnoFlagzombieEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}