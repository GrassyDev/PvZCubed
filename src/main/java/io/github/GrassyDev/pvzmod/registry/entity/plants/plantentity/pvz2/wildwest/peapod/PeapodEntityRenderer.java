package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.wildwest.peapod;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.PeapodVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class PeapodEntityRenderer extends GeoEntityRenderer<PeapodEntity> {

	public static final Map<PeapodVariants, Identifier> LOCATION_BY_VARIANT =
		Util.make(Maps.newEnumMap(PeapodVariants.class), (map) -> {
			map.put(PeapodVariants.DEFAULT,
					new Identifier(PvZCubed.MOD_ID, "textures/entity/peashooter/peapod.png"));
			map.put(PeapodVariants.PLURAL,
					new Identifier(PvZCubed.MOD_ID, "textures/entity/peashooter/peapod_plural.png"));
		});

	public Identifier getTextureResource(PeapodEntity object) {
		return LOCATION_BY_VARIANT.get(object.getVariant());
	}
    public PeapodEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new PeapodEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

}
