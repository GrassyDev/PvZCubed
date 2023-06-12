package io.github.GrassyDev.pvzmod.registry.entity.environment.snowtile;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SnowTileRenderer extends GeoEntityRenderer<SnowTile> {

    public SnowTileRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SnowTileModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }
}
