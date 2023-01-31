package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.snowqueenpea;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.SnowQueenPeaVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SnowqueenpeaEntityRenderer extends GeoEntityRenderer<SnowqueenpeaEntity> {

	public static final Map<SnowQueenPeaVariants, Identifier> LOCATION_BY_VARIANT =
			Util.make(Maps.newEnumMap(SnowQueenPeaVariants.class), (map) -> {
				map.put(SnowQueenPeaVariants.DEFAULT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/peashooter/snowpea.png"));
				map.put(SnowQueenPeaVariants.LESBIAN,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/peashooter/snowqueenpea_lesbian.png"));
				map.put(SnowQueenPeaVariants.BISEXUAL,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/peashooter/snowpea_bi.png"));
			});

    public SnowqueenpeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SnowqueenpeaEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

}
