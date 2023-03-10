package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.bully.basketballcarrier;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.BullyVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class BasketballCarrierEntityRenderer extends GeoEntityRenderer<BasketballCarrierEntity> {

    public BasketballCarrierEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BasketballCarrierEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

	public static final Map<BullyVariants, Identifier> LOCATION_MODEL_BY_VARIANT =
			Util.make(Maps.newEnumMap(BullyVariants.class), (map) -> {
				map.put(BullyVariants.BASKET,
				        new Identifier("pvzmod", "geo/basketballcarrier.geo.json"));
				map.put(BullyVariants.BASKETHYPNO,
						new Identifier("pvzmod", "geo/basketballcarrier.geo.json"));
			});

	public Identifier getModelResource(BasketballCarrierEntity object) {
		return LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
	}

}
