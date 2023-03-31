package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombiemachines.robocone;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class RoboConeEntityRenderer extends GeoEntityRenderer<RoboConeEntity> {

    public RoboConeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new RoboConeEntityModel());
        this.shadowRadius = 1F; //change 0.7 to the desired shadow size.
    }
}
