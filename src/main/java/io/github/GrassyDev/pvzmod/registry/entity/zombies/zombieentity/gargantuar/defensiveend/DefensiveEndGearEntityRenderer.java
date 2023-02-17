package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.defensiveend;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class DefensiveEndGearEntityRenderer extends GeoEntityRenderer<DefensiveEndGearEntity> {

    public DefensiveEndGearEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new DefensiveEndGearEntityModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }

}
