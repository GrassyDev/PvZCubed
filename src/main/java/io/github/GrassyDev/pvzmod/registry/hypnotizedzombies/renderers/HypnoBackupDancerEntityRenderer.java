package io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.renderers;

import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoBackupDancerEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.models.HypnoBackupDancerEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class HypnoBackupDancerEntityRenderer extends GeoEntityRenderer<HypnoBackupDancerEntity> {

    public HypnoBackupDancerEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new HypnoBackupDancerEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
