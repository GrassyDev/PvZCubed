package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.bully.basic;

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
public class BullyEntityRenderer extends GeoEntityRenderer<BullyEntity> {

    public BullyEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BullyEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

	public static final Map<BullyVariants, Identifier> LOCATION_MODEL_BY_VARIANT =
			Util.make(Maps.newEnumMap(BullyVariants.class), (map) -> {
				map.put(BullyVariants.BULLY,
				        new Identifier("pvzmod", "geo/bully.geo.json"));
				map.put(BullyVariants.BULLYHYPNO,
						new Identifier("pvzmod", "geo/bully.geo.json"));
			});

	public Identifier getModelResource(BullyEntity object) {
		return LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
	}

}
