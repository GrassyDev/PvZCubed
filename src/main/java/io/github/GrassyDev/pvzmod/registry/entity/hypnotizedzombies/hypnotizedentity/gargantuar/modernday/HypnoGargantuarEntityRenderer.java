package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.gargantuar.modernday;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class HypnoGargantuarEntityRenderer extends GeoEntityRenderer<HypnoGargantuarEntity> {

    public HypnoGargantuarEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new HypnoGargantuarEntityModel());
        this.shadowRadius = 1.5F; //change 0.7 to the desired shadow size.
    }

}
