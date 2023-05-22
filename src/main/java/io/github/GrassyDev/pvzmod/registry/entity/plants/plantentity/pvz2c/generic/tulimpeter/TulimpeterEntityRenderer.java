package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.tulimpeter;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.TulipVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class TulimpeterEntityRenderer extends GeoEntityRenderer<TulimpeterEntity> {

	public static final Map<TulipVariants, Identifier> LOCATION_BY_VARIANT =
			Util.make(Maps.newEnumMap(TulipVariants.class), (map) -> {
				map.put(TulipVariants.DEFAULT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/tulimpeter/tulimpeter.png"));
				map.put(TulipVariants.HEAL,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/tulimpeter/tulimpeter_heal.png"));
				map.put(TulipVariants.HYPNO,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/tulimpeter/tulimpeter_hypno.png"));
			});

	public Identifier getTextureResource(TulimpeterEntity object) {
		return LOCATION_BY_VARIANT.get(object.getVariant());
	}

    public TulimpeterEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new TulimpeterEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(TulimpeterEntity plantEntity, BlockPos blockPos) {
		return Math.min(super.getBlockLight(plantEntity, blockPos) + 3, 15);
	}
}
