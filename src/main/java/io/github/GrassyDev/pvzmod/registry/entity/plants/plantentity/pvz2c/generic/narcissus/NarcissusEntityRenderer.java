package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.narcissus;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class NarcissusEntityRenderer extends GeoEntityRenderer<NarcissusEntity> {

    public NarcissusEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new NarcissusEntityModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }

}
