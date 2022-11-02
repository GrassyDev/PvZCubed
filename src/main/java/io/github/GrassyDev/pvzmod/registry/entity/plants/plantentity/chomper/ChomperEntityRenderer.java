package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.chomper;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.ChomperVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class ChomperEntityRenderer extends GeoEntityRenderer<ChomperEntity> {

	public static final Map<ChomperVariants, Identifier> LOCATION_BY_VARIANT =
			Util.make(Maps.newEnumMap(ChomperVariants.class), (map) -> {
				map.put(ChomperVariants.DEFAULT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/chomper/chomper.png"));
				map.put(ChomperVariants.DEMIGIRL,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/chomper/chomper_dg.png"));
				map.put(ChomperVariants.ENBY,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/chomper/chomper_enby.png"));
				map.put(ChomperVariants.PIRANHAPLANT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/chomper/chomper_piranhaplant.png"));
			});

    public ChomperEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ChomperEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
