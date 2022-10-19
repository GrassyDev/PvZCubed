package net.fabricmc.example.registry.hypnotizedzombies.renderers;

import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoBrowncoatEntity;
import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoPoleVaultingEntity;
import net.fabricmc.example.registry.hypnotizedzombies.models.HypnoBrowncoatEntityModel;
import net.fabricmc.example.registry.hypnotizedzombies.models.HypnoPoleVaultingEntityModel;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class HypnoPoleVaultingEntityRenderer extends GeoEntityRenderer<HypnoPoleVaultingEntity> {

    public HypnoPoleVaultingEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new HypnoPoleVaultingEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}