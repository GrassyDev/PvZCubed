package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.football;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.FootballVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class FootballEntityRenderer extends GeoEntityRenderer<FootballEntity> {

    public FootballEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new FootballEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

	public static final Map<FootballVariants, Identifier> LOCATION_BY_VARIANT =
			Util.make(Maps.newEnumMap(FootballVariants.class), (map) -> {
				map.put(FootballVariants.DEFAULT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/football/football.png"));
				map.put(FootballVariants.BERSERKER,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/football/berserker.png"));
			});

	public Identifier getTextureResource(FootballEntity object) {
		return LOCATION_BY_VARIANT.get(object.getVariant());
	}

	public static final Map<FootballVariants, Identifier> LOCATION_MODEL_BY_VARIANT =
			Util.make(Maps.newEnumMap(FootballVariants.class), (map) -> {
				map.put(FootballVariants.DEFAULT,
						new Identifier("pvzmod", "geo/football.geo.json"));
				map.put(FootballVariants.BERSERKER,
						new Identifier("pvzmod", "geo/berserker.geo.json"));
			});

	public Identifier getModelResource(FootballEntity object) {
		return LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
	}

}
