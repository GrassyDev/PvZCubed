package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.admiralnavybean;

import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.navybean.NavyBeanEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.navybean.NavyBeanEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class AdmiralNavyBeanEntityRenderer extends GeoEntityRenderer<AdmiralNavyBeanEntity> {

    public AdmiralNavyBeanEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new AdmiralNavyBeanEntityModel());
        this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
    }

}
