package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.fumeshroom;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.FumeshroomVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class FumeshroomEntityRenderer extends GeoEntityRenderer<FumeshroomEntity> {

	public static final Map<FumeshroomVariants, Identifier> LOCATION_BY_VARIANT =
			Util.make(Maps.newEnumMap(FumeshroomVariants.class), (map) -> {
				map.put(FumeshroomVariants.DEFAULT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/fumeshroom/fumeshroom.png"));
				map.put(FumeshroomVariants.GAY,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/fumeshroom/fumeshroom_g.png"));
				map.put(FumeshroomVariants.TRANS,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/fumeshroom/fumeshroom_t.png"));
			});

	public Identifier getTextureResource(FumeshroomEntity object) {
		return LOCATION_BY_VARIANT.get(object.getVariant());
	}

    public FumeshroomEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new FumeshroomEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
