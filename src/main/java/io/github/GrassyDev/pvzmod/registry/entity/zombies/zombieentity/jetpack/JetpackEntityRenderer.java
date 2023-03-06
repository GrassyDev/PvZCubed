package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.jetpack;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.BrowncoatVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class JetpackEntityRenderer extends GeoEntityRenderer<JetpackEntity> {

    public JetpackEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new JetpackEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

	public static final Map<BrowncoatVariants, Identifier> LOCATION_MODEL_BY_VARIANT =
			Util.make(Maps.newEnumMap(BrowncoatVariants.class), (map) -> {
				map.put(BrowncoatVariants.BROWNCOAT,
				        new Identifier("pvzmod", "geo/jetpack.geo.json"));
				map.put(BrowncoatVariants.BROWNCOATHYPNO,
						new Identifier("pvzmod", "geo/jetpack.geo.json"));
			});

	public Identifier getModelResource(JetpackEntity object) {
		return LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
	}

}
