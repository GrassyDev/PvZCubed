package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.newspaper;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.NewspaperVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class NewspaperEntityRenderer extends GeoEntityRenderer<NewspaperEntity> {

    public NewspaperEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new NewspaperEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

	public static final Map<NewspaperVariants, Identifier> LOCATION_MODEL_BY_VARIANT =
			Util.make(Maps.newEnumMap(NewspaperVariants.class), (map) -> {
				map.put(NewspaperVariants.DEFAULT,
						new Identifier("pvzmod", "geo/newspaper.geo.json"));
				map.put(NewspaperVariants.DEFAULTHYPNO,
						new Identifier("pvzmod", "geo/newspaper.geo.json"));
				map.put(NewspaperVariants.SUNDAYEDITION,
						new Identifier("pvzmod", "geo/sundayedition.geo.json"));
				map.put(NewspaperVariants.SUNDAYEDITIONHYPNO,
						new Identifier("pvzmod", "geo/sundayedition.geo.json"));
			});

	public Identifier getModelResource(NewspaperEntity object) {
		return LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
	}

}
