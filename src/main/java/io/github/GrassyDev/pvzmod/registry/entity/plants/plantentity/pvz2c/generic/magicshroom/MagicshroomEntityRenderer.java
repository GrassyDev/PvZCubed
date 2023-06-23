package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.magicshroom;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class MagicshroomEntityRenderer extends GeoEntityRenderer<MagicshroomEntity> {

    public MagicshroomEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new MagicshroomEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
