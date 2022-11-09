package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.perfumeshroom;


import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.doomshroom.DoomshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.doomshroom.DoomshroomEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class PerfoomshroomEntityRenderer extends GeoEntityRenderer<PerfoomshroomEntity> {

    public PerfoomshroomEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new PerfoomshroomEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
