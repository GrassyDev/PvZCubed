package io.github.GrassyDev.pvzmod.registry.entity.environment.icetile;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class IceTileRenderer extends GeoEntityRenderer<IceTile> {

    public IceTileRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new IceTileModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }
}
