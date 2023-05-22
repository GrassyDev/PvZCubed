package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.SunflowerVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SunflowerEntityRenderer extends GeoEntityRenderer<SunflowerEntity> {

	public static final Map<SunflowerVariants, Identifier> LOCATION_BY_VARIANT =
			Util.make(Maps.newEnumMap(SunflowerVariants.class), (map) -> {
				map.put(SunflowerVariants.DEFAULT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/sunflower/pvzsunflower.png"));
				map.put(SunflowerVariants.LESBIAN,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/sunflower/pvzsunflower_lesbian.png"));
				map.put(SunflowerVariants.WLW,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/sunflower/pvzsunflower_wlw.png"));
				map.put(SunflowerVariants.MLM,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/sunflower/pvzsunflower_mlm.png"));
			});

    public SunflowerEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SunflowerEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(SunflowerEntity plantEntity, BlockPos blockPos) {
		return Math.min(super.getBlockLight(plantEntity, blockPos) + 5, 15);
	}

}
