package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.defensiveend;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class DefensiveEndEntityRenderer extends GeoEntityRenderer<DefensiveEndEntity> {

	public DefensiveEndEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new DefensiveEndEntityModel());
		this.shadowRadius = 1.5F; //change 0.7 to the desired shadow size.
	}

}
