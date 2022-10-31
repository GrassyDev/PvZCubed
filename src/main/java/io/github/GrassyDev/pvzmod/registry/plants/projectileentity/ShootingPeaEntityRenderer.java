package io.github.GrassyDev.pvzmod.registry.plants.projectileentity;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.plants.plantentity.peapod.PeapodEntity;
import io.github.GrassyDev.pvzmod.registry.variants.plants.PeapodVariants;
import io.github.GrassyDev.pvzmod.registry.variants.projectiles.ShootingPeaVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

import java.util.Map;

public class ShootingPeaEntityRenderer extends GeoProjectilesRenderer {

	public static final Map<ShootingPeaVariants, Identifier> LOCATION_BY_VARIANT =
			Util.make(Maps.newEnumMap(ShootingPeaVariants.class), (map) -> {
				map.put(ShootingPeaVariants.DEFAULT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/peashot.png"));
				map.put(ShootingPeaVariants.BLACK,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/peashot_black.png"));
				map.put(ShootingPeaVariants.PURPLE,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/peashot_purple.png"));
				map.put(ShootingPeaVariants.BLUE,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/peashot_blue.png"));
				map.put(ShootingPeaVariants.CYAN,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/peashot_cyan.png"));
			});

	public Identifier getTextureResource(ShootingPeaEntity object) {
		return LOCATION_BY_VARIANT.get(object.getVariant());
	}

	public ShootingPeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingPeaEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}

}
