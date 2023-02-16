package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.jalapeno;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class JalapenoEntityRenderer extends GeoEntityRenderer<JalapenoEntity> {

    public JalapenoEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new JalapenoEntityModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

}
