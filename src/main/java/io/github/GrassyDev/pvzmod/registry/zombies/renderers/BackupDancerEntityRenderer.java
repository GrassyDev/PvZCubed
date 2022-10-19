package net.fabricmc.example.registry.zombies.renderers;

import net.fabricmc.example.registry.zombies.models.BackupDancerEntityModel;
import net.fabricmc.example.registry.zombies.models.DancingZombieEntityModel;
import net.fabricmc.example.registry.zombies.zombieentity.BackupDancerEntity;
import net.fabricmc.example.registry.zombies.zombieentity.DancingZombieEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class BackupDancerEntityRenderer extends GeoEntityRenderer<BackupDancerEntity> {

    public BackupDancerEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new BackupDancerEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}