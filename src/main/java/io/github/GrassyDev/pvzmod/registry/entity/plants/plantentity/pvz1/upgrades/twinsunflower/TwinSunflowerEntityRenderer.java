package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.twinsunflower;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.TwinSunflowerVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class TwinSunflowerEntityRenderer extends GeoEntityRenderer<TwinSunflowerEntity> {

	public static final Map<TwinSunflowerVariants, Identifier> LOCATION_BY_VARIANT =
			Util.make(Maps.newEnumMap(TwinSunflowerVariants.class), (map) -> {
				map.put(TwinSunflowerVariants.DEFAULT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/sunflower/pvzsunflower.png"));
				map.put(TwinSunflowerVariants.LESBIAN,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/sunflower/pvzsunflower_lesbian.png"));
				map.put(TwinSunflowerVariants.WLW,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/sunflower/pvzsunflower_wlw.png"));
				map.put(TwinSunflowerVariants.MLM,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/sunflower/pvzsunflower_mlm.png"));
				map.put(TwinSunflowerVariants.WLW_MLM,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/sunflower/pvzsunflower_wlw_n_mlm.png"));
				map.put(TwinSunflowerVariants.LESBIAN_WLW,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/sunflower/pvzsunflower_lesbian_n_wlw.png"));
			});

    public TwinSunflowerEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new TwinSunflowerEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(TwinSunflowerEntity plantEntity, BlockPos blockPos) {
		return Math.min(super.getBlockLight(plantEntity, blockPos) + 7, 15);
	}

}
