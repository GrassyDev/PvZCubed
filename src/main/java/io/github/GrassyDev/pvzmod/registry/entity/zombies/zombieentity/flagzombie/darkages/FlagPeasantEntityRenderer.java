package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.darkages;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class FlagPeasantEntityRenderer extends GeoEntityRenderer<FlagPeasantEntity> {

    public FlagPeasantEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new FlagPeasantEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
