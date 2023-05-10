package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.pirateseas.springbean;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SpringbeanEntityRenderer extends GeoEntityRenderer<SpringbeanEntity> {

    public SpringbeanEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SpringbeanEntityModel());
        this.shadowRadius = 0.25F; //change 0.7 to the desired shadow size.
    }

}
