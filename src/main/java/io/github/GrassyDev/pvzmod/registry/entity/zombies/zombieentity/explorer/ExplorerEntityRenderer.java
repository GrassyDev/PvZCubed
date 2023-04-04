package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.explorer;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.ExplorerVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class ExplorerEntityRenderer extends GeoEntityRenderer<ExplorerEntity> {

    public ExplorerEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ExplorerEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

	public static final Map<ExplorerVariants, Identifier> LOCATION_MODEL_BY_VARIANT =
			Util.make(Maps.newEnumMap(ExplorerVariants.class), (map) -> {
				map.put(ExplorerVariants.EXPLORER,
						new Identifier("pvzmod", "geo/explorer.geo.json"));
				map.put(ExplorerVariants.EXPLORERHYPNO,
						new Identifier("pvzmod", "geo/explorer.geo.json"));
				map.put(ExplorerVariants.TORCHLIGHT,
						new Identifier("pvzmod", "geo/torchlight.geo.json"));
				map.put(ExplorerVariants.TORCHLIGHTHYPNO,
						new Identifier("pvzmod", "geo/torchlight.geo.json"));
			});

	public Identifier getModelResource(ExplorerEntity object) {
		return LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
	}

}
