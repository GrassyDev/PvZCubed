package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.superfan;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.ImpVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SuperFanImpEntityRenderer extends GeoEntityRenderer<SuperFanImpEntity> {

    public SuperFanImpEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SuperFanImpEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

	public static final Map<ImpVariants, Identifier> LOCATION_BY_VARIANT =
			Util.make(Maps.newEnumMap(ImpVariants.class), (map) -> {
				map.put(ImpVariants.DEFAULT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/imp/superfanimp.png"));
				map.put(ImpVariants.NEWYEAR,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/imp/newyearimp.png"));
			});

	public Identifier getTextureResource(SuperFanImpEntity object) {
		return LOCATION_BY_VARIANT.get(object.getVariant());
	}
}
