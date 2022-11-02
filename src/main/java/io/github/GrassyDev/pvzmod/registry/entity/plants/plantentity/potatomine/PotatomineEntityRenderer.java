package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.potatomine;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class PotatomineEntityRenderer extends GeoEntityRenderer<PotatomineEntity> {

    public PotatomineEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new PotatomineEntityModel());
        this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
    }

}
