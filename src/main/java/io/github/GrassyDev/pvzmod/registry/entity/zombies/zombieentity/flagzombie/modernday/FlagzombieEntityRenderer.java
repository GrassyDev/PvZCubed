package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.modernday;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.fumeshroom.FumeshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.FumeshroomVariants;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.FlagZombieVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class FlagzombieEntityRenderer extends GeoEntityRenderer<FlagzombieEntity> {

	public static final Map<FlagZombieVariants, Identifier> LOCATION_BY_VARIANT =
			Util.make(Maps.newEnumMap(FlagZombieVariants.class), (map) -> {
				map.put(FlagZombieVariants.DEFAULT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/browncoat/flagzombie/flagzombie.png"));
				map.put(FlagZombieVariants.GAY,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/browncoat/flagzombie/flagzombie_g.png"));
				map.put(FlagZombieVariants.TRANS,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/browncoat/flagzombie/flagzombie_t.png"));
			});

	public Identifier getTextureResource(FlagzombieEntity object) {
		return LOCATION_BY_VARIANT.get(object.getVariant());
	}

    public FlagzombieEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new FlagzombieEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
