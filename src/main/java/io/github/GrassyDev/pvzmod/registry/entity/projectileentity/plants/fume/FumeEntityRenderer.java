package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.fume;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pea.ShootingPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.projectiles.FumeVariants;
import io.github.GrassyDev.pvzmod.registry.entity.variants.projectiles.ShootingPeaVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

import java.util.Map;

public class FumeEntityRenderer extends GeoProjectilesRenderer {

	public FumeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new FumeEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}

}
