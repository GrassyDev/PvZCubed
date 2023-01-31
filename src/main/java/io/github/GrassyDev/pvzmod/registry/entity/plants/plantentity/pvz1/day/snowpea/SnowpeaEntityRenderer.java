package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.snowpea;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.SnowPeaVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SnowpeaEntityRenderer extends GeoEntityRenderer<SnowpeaEntity> {

	public static final Map<SnowPeaVariants, Identifier> LOCATION_BY_VARIANT =
			Util.make(Maps.newEnumMap(SnowPeaVariants.class), (map) -> {
				map.put(SnowPeaVariants.DEFAULT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/peashooter/snowpea.png"));
				map.put(SnowPeaVariants.MLM,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/peashooter/snowpea_mlm.png"));
				map.put(SnowPeaVariants.BISEXUAL,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/peashooter/snowpea_bi.png"));
			});

    public SnowpeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SnowpeaEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

}
