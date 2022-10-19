package io.github.GrassyDev.pvzmod.registry.zombies.renderers;

import io.github.GrassyDev.pvzmod.registry.zombies.models.FootballEntityModel;
import io.github.GrassyDev.pvzmod.registry.zombies.zombieentity.FootballEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class FootballEntityRenderer extends GeoEntityRenderer<FootballEntity> {

    public FootballEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new FootballEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
