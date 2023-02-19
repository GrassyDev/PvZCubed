package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
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

	public static final Map<BrowncoatVariants, Identifier> LOCATION_BY_VARIANT =
			Util.make(Maps.newEnumMap(BrowncoatVariants.class), (map) -> {
				map.put(BrowncoatVariants.DEFAULT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/browncoat/browncoat.png"));
				map.put(BrowncoatVariants.CONEHEAD,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/browncoat/conehead.png"));
				map.put(BrowncoatVariants.BUCKETHEAD,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/browncoat/buckethead.png"));
				map.put(BrowncoatVariants.SCREENDOOR,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/browncoat/screendoor.png"));
			});

	public Identifier getTextureResource(BrowncoatEntity object) {
		return LOCATION_BY_VARIANT.get(object.getVariant());
	}

	public static final Map<BrowncoatVariants, Identifier> LOCATION_MODEL_BY_VARIANT =
			Util.make(Maps.newEnumMap(BrowncoatVariants.class), (map) -> {
				map.put(BrowncoatVariants.DEFAULT,
				        new Identifier("pvzmod", "geo/browncoat.geo.json"));
				map.put(BrowncoatVariants.CONEHEAD,
						new Identifier("pvzmod", "geo/conehead.geo.json"));
				map.put(BrowncoatVariants.BUCKETHEAD,
						new Identifier("pvzmod", "geo/buckethead.geo.json"));
				map.put(BrowncoatVariants.SCREENDOOR,
						new Identifier("pvzmod", "geo/screendoor.geo.json"));
			});

	public Identifier getModelResource(BrowncoatEntity object) {
		return LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
	}

}
