package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.darkages;

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
public class PeasantEntityRenderer extends GeoEntityRenderer<PeasantEntity> {

    public PeasantEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new PeasantEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

	public static final Map<BrowncoatVariants, Identifier> LOCATION_MODEL_BY_VARIANT =
			Util.make(Maps.newEnumMap(BrowncoatVariants.class), (map) -> {
				map.put(BrowncoatVariants.BROWNCOAT,
				        new Identifier("pvzmod", "geo/peasant.geo.json"));
				map.put(BrowncoatVariants.BROWNCOATHYPNO,
						new Identifier("pvzmod", "geo/peasant.geo.json"));
				map.put(BrowncoatVariants.CONEHEAD,
						new Identifier("pvzmod", "geo/peasantcone.geo.json"));
				map.put(BrowncoatVariants.CONEHEADHYPNO,
						new Identifier("pvzmod", "geo/peasantcone.geo.json"));
				map.put(BrowncoatVariants.BUCKETHEAD,
						new Identifier("pvzmod", "geo/peasantbucket.geo.json"));
				map.put(BrowncoatVariants.BUCKETHEADHYPNO,
						new Identifier("pvzmod", "geo/peasantbucket.geo.json"));
				map.put(BrowncoatVariants.PEASANTKNIGHT,
						new Identifier("pvzmod", "geo/peasantknight.geo.json"));
				map.put(BrowncoatVariants.PEASANTKNIGHTHYPNO,
						new Identifier("pvzmod", "geo/peasantknight.geo.json"));
			});

	public Identifier getModelResource(PeasantEntity object) {
		return LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
	}

}
