package io.github.GrassyDev.pvzmod.registry.zombies.renderers;

import io.github.GrassyDev.pvzmod.registry.zombies.models.ConeheadEntityModel;
import io.github.GrassyDev.pvzmod.registry.zombies.zombieentity.ConeheadEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class ConeheadEntityRenderer extends GeoEntityRenderer<ConeheadEntity> {

    public ConeheadEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ConeheadEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
