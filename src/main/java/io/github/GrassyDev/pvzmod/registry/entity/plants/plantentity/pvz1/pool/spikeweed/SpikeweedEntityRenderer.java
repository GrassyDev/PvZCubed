package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.spikeweed;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SpikeweedEntityRenderer extends GeoEntityRenderer<SpikeweedEntity> {

    public SpikeweedEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SpikeweedEntityModel());
        this.shadowRadius = 0.65F; //change 0.7 to the desired shadow size.
    }

}
