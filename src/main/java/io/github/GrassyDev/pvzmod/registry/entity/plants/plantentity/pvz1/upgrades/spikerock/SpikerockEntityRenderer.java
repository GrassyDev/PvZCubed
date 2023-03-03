package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.spikerock;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SpikerockEntityRenderer extends GeoEntityRenderer<SpikerockEntity> {

    public SpikerockEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SpikerockEntityModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }
}
