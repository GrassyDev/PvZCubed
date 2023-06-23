package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.scaredyshroom;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.fumeshroom.FumeshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.ScaredyshroomVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class ScaredyshroomEntityRenderer extends GeoEntityRenderer<ScaredyshroomEntity> {

	public static final Map<ScaredyshroomVariants, Identifier> LOCATION_BY_VARIANT =
			Util.make(Maps.newEnumMap(ScaredyshroomVariants.class), (map) -> {
				map.put(ScaredyshroomVariants.DEFAULT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/puffshroom/scaredyshroom.png"));
				map.put(ScaredyshroomVariants.DEMIBOY,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/puffshroom/scaredyshroom_db.png"));
				map.put(ScaredyshroomVariants.LINK,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/puffshroom/scaredyshroom_link.png"));
			});

	public Identifier getTextureResource(FumeshroomEntity object) {
		return LOCATION_BY_VARIANT.get(object.getVariant());
	}

    public ScaredyshroomEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ScaredyshroomEntityModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(ScaredyshroomEntity plantEntity, BlockPos blockPos) {
		return Math.min(super.getBlockLight(plantEntity, blockPos) + 4, 15);
	}

}
