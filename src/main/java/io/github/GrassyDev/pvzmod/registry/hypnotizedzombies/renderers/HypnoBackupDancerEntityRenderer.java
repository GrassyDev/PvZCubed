package net.fabricmc.example.registry.hypnotizedzombies.renderers;

import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoBackupDancerEntity;
import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoDancingZombieEntity;
import net.fabricmc.example.registry.hypnotizedzombies.models.HypnoBackupDancerEntityModel;
import net.fabricmc.example.registry.hypnotizedzombies.models.HypnoDancingZombieEntityModel;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class HypnoBackupDancerEntityRenderer extends GeoEntityRenderer<HypnoBackupDancerEntity> {

    public HypnoBackupDancerEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new HypnoBackupDancerEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}