package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.flagzombie.modernday;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.FlagZombieVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.modernday.FlagzombieEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class HypnoFlagzombieEntityRenderer extends GeoEntityRenderer<HypnoFlagzombieEntity> {

	public static final Map<FlagZombieVariants, Identifier> LOCATION_BY_VARIANT =
			Util.make(Maps.newEnumMap(FlagZombieVariants.class), (map) -> {
				map.put(FlagZombieVariants.DEFAULT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/browncoat/flagzombie/flagzombie_hypnotized.png"));
				map.put(FlagZombieVariants.GAY,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/browncoat/flagzombie/flagzombie_g_hypnotized.png"));
				map.put(FlagZombieVariants.TRANS,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/browncoat/flagzombie/flagzombie_t_hypnotized.png"));
			});

	public Identifier getTextureResource(HypnoFlagzombieEntity object) {
		return LOCATION_BY_VARIANT.get(object.getVariant());
	}

    public HypnoFlagzombieEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new HypnoFlagzombieEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
