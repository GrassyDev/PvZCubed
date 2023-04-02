package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.mummy;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class FlagMummyEntityRenderer extends GeoEntityRenderer<FlagMummyEntity> {

    public FlagMummyEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new FlagMummyEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
