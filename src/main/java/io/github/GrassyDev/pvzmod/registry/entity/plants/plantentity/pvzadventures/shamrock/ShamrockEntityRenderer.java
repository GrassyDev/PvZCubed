package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.shamrock;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.ShamrockVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class ShamrockEntityRenderer extends GeoEntityRenderer<ShamrockEntity> {

	public static final Map<ShamrockVariants, Identifier> LOCATION_BY_VARIANT =
			Util.make(Maps.newEnumMap(ShamrockVariants.class), (map) -> {
				map.put(ShamrockVariants.DEFAULT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/shamrock/shamrock.png"));
				map.put(ShamrockVariants.PRIDE,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/shamrock/shamrock_pride.png"));
			});

	public Identifier getTextureResource(ShamrockEntity object) {
		return LOCATION_BY_VARIANT.get(object.getVariant());
	}

    public ShamrockEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShamrockEntityModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

}
