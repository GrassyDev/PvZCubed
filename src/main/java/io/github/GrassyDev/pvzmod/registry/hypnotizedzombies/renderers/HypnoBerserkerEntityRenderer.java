package net.fabricmc.example.registry.hypnotizedzombies.renderers;

import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoBerserkerEntity;
import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoFootballEntity;
import net.fabricmc.example.registry.hypnotizedzombies.models.HypnoBerserkerEntityModel;
import net.fabricmc.example.registry.hypnotizedzombies.models.HypnoFootballEntityModel;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class HypnoBerserkerEntityRenderer extends GeoEntityRenderer<HypnoBerserkerEntity> {

    public HypnoBerserkerEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new HypnoBerserkerEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}