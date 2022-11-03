package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.conehead.modernday;


import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class HypnoConeheadEntityRenderer extends GeoEntityRenderer<HypnoConeheadEntity> {

    public HypnoConeheadEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new HypnoConeheadEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
