package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.iceshroom;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class IceshroomEntityRenderer extends GeoEntityRenderer<IceshroomEntity> {

    public IceshroomEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new IceshroomEntityModel());
        this.shadowRadius = 0.6F; //change 0.7 to the desired shadow size.
    }

}
