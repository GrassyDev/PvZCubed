package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.announcer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class AnnouncerImpEntityRenderer extends GeoEntityRenderer<AnnouncerImpEntity> {

    public AnnouncerImpEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new AnnouncerImpEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

}
