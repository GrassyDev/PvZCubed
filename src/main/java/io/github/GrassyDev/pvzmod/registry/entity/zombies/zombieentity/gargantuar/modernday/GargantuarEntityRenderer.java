package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.GargantuarVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class GargantuarEntityRenderer extends GeoEntityRenderer<GargantuarEntity> {

    public GargantuarEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new GargantuarEntityModel());
        this.shadowRadius = 1.5F; //change 0.7 to the desired shadow size.
    }

	public static final Map<GargantuarVariants, Identifier> LOCATION_MODEL_BY_VARIANT =
			Util.make(Maps.newEnumMap(GargantuarVariants.class), (map) -> {
				map.put(GargantuarVariants.GARGANTUAR,
						new Identifier("pvzmod", "geo/gargantuar.geo.json"));
				map.put(GargantuarVariants.GARGANTUARHYPNO,
						new Identifier("pvzmod", "geo/gargantuar.geo.json"));
				map.put(GargantuarVariants.DEFENSIVEEND,
						new Identifier("pvzmod", "geo/defensiveend.geo.json"));
				map.put(GargantuarVariants.DEFENSIVEENDHYPNO,
						new Identifier("pvzmod", "geo/defensiveend.geo.json"));
				map.put(GargantuarVariants.DEFENSIVEEND_NEWYEAR,
						new Identifier("pvzmod", "geo/defensiveend.geo.json"));
				map.put(GargantuarVariants.DEFENSIVEEND_NEWYEARHYPNO,
						new Identifier("pvzmod", "geo/defensiveend.geo.json"));
			});

	public Identifier getModelResource(GargantuarEntity object) {
		return LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
	}

}
