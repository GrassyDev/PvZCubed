package io.github.GrassyDev.pvzmod.registry.zombies.renderers;

import io.github.GrassyDev.pvzmod.registry.zombies.models.BrowncoatEntityModel;
import io.github.GrassyDev.pvzmod.registry.zombies.zombieentity.BrowncoatEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class BrowncoatEntityRenderer extends GeoEntityRenderer<BrowncoatEntity> {

    public BrowncoatEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BrowncoatEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
