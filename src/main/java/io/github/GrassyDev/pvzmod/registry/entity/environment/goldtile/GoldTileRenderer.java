package io.github.GrassyDev.pvzmod.registry.entity.environment.goldtile;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class GoldTileRenderer extends GeoEntityRenderer<GoldTile> {

    public GoldTileRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new GoldTileModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(GoldTile tile, BlockPos blockPos) {
		return tile.powered? 15 : 0;
	}

}
