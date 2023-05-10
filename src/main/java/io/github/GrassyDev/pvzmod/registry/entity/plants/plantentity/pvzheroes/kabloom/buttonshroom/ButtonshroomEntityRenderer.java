package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.kabloom.buttonshroom;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class ButtonshroomEntityRenderer extends GeoEntityRenderer<ButtonshroomEntity> {

    public ButtonshroomEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ButtonshroomEntityModel());
        this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
    }

}
