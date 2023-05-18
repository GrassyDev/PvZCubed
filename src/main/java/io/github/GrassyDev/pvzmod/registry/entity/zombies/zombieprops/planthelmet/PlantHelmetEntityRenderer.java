package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.planthelmet;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class PlantHelmetEntityRenderer extends GeoEntityRenderer<PlantHelmetEntity> {

    public PlantHelmetEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new PlantHelmetEntityModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }

}
