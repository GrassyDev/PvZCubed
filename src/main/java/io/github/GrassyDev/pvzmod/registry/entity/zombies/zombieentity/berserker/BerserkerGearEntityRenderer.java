package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.berserker;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class BerserkerGearEntityRenderer extends GeoEntityRenderer<BerserkerGearEntity> {

    public BerserkerGearEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BerserkerGearEntityModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }

}
