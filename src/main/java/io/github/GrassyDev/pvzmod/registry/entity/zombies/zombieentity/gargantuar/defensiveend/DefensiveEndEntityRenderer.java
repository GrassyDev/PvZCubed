package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.defensiveend;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.DefensiveEndVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.superfan.SuperFanImpEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class DefensiveEndEntityRenderer extends GeoEntityRenderer<DefensiveEndEntity> {

	public DefensiveEndEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new DefensiveEndEntityModel());
		this.shadowRadius = 1.5F; //change 0.7 to the desired shadow size.
	}

	public static final Map<DefensiveEndVariants, Identifier> LOCATION_BY_VARIANT =
			Util.make(Maps.newEnumMap(DefensiveEndVariants.class), (map) -> {
				map.put(DefensiveEndVariants.DEFAULT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/gargantuar/defensiveend.png"));
				map.put(DefensiveEndVariants.NEWYEARIMP,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/gargantuar/defensiveend_newyear.png"));
			});

	public Identifier getTextureResource(DefensiveEndEntity object) {
		return LOCATION_BY_VARIANT.get(object.getVariant());
	}
}
