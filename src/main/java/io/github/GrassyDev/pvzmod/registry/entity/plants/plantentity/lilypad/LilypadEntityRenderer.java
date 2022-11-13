package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.lilypad;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class LilypadEntityRenderer extends GeoEntityRenderer<LilyPadEntity> {

    public LilypadEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new LilyPadEntityModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }

}
