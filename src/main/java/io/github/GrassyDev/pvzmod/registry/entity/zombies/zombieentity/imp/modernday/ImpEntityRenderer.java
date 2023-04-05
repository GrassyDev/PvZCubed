package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.modernday;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.ImpVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class ImpEntityRenderer extends GeoEntityRenderer<ImpEntity> {

    public ImpEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ImpEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

	public static final Map<ImpVariants, Identifier> LOCATION_MODEL_BY_VARIANT =
			Util.make(Maps.newEnumMap(ImpVariants.class), (map) -> {
				map.put(ImpVariants.DEFAULT,
						new Identifier("pvzmod", "geo/imp.geo.json"));
				map.put(ImpVariants.DEFAULTHYPNO,
						new Identifier("pvzmod", "geo/imp.geo.json"));
				map.put(ImpVariants.IMPDRAGON,
						new Identifier("pvzmod", "geo/impdragon.geo.json"));
				map.put(ImpVariants.IMPDRAGONHYPNO,
						new Identifier("pvzmod", "geo/impdragon.geo.json"));
			});

	public Identifier getModelResource(ImpEntity object) {
		return LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
	}

}
