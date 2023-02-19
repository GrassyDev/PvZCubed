package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday;

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
public class BrowncoatEntityRenderer extends GeoEntityRenderer<BrowncoatEntity> {

    public BrowncoatEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BrowncoatEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

	public static final Map<BrowncoatVariants, Identifier> LOCATION_MODEL_BY_VARIANT =
			Util.make(Maps.newEnumMap(BrowncoatVariants.class), (map) -> {
				map.put(BrowncoatVariants.BROWNCOAT,
				        new Identifier("pvzmod", "geo/browncoat.geo.json"));
				map.put(BrowncoatVariants.BROWNCOATHYPNO,
						new Identifier("pvzmod", "geo/browncoat.geo.json"));
				map.put(BrowncoatVariants.CONEHEAD,
						new Identifier("pvzmod", "geo/conehead.geo.json"));
				map.put(BrowncoatVariants.CONEHEADHYPNO,
						new Identifier("pvzmod", "geo/conehead.geo.json"));
				map.put(BrowncoatVariants.BUCKETHEAD,
						new Identifier("pvzmod", "geo/buckethead.geo.json"));
				map.put(BrowncoatVariants.BUCKETHEADHYPNO,
						new Identifier("pvzmod", "geo/buckethead.geo.json"));
				map.put(BrowncoatVariants.SCREENDOOR,
						new Identifier("pvzmod", "geo/screendoor.geo.json"));
				map.put(BrowncoatVariants.SCREENDOORHYPNO,
						new Identifier("pvzmod", "geo/screendoor.geo.json"));
			});

	public Identifier getModelResource(BrowncoatEntity object) {
		return LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
	}

}
