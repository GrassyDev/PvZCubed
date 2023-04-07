package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.jetpack;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.JetpackVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.dancingzombie.DancingZombieEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class JetpackEntityRenderer extends GeoEntityRenderer<JetpackEntity> {

    public JetpackEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new JetpackEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

	public static final Map<JetpackVariants, Identifier> LOCATION_MODEL_BY_VARIANT =
			Util.make(Maps.newEnumMap(JetpackVariants.class), (map) -> {
				map.put(JetpackVariants.JETPACK,
				        new Identifier("pvzmod", "geo/jetpack.geo.json"));
				map.put(JetpackVariants.JETPACKHYPNO,
						new Identifier("pvzmod", "geo/jetpack.geo.json"));
				map.put(JetpackVariants.BLASTRONAUT,
						new Identifier("pvzmod", "geo/blastronaut.geo.json"));
				map.put(JetpackVariants.BLASTRONAUTHYPNO,
						new Identifier("pvzmod", "geo/blastronaut.geo.json"));
			});

	public Identifier getModelResource(JetpackEntity object) {
		return LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
	}

	protected int getBlockLight(DancingZombieEntity zombieEntity, BlockPos blockPos) {
		return 7;
	}
}
