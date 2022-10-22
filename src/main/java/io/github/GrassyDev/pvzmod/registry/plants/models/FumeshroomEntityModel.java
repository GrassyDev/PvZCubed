package io.github.GrassyDev.pvzmod.registry.plants.models;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.plants.plantentity.FumeshroomEntity;
import io.github.GrassyDev.pvzmod.registry.plants.renderers.FumeshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.variants.plants.FumeshroomVariants;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import java.util.Map;

public class FumeshroomEntityModel extends AnimatedGeoModel<FumeshroomEntity> {

    @Override
    public Identifier getModelResource(FumeshroomEntity object)
    {
        return new Identifier("pvzmod", "geo/fumeshroom.geo.json");
    }

	public Identifier getTextureResource(FumeshroomEntity object) {
		return FumeshroomEntityRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
	}

    @Override
    public Identifier getAnimationResource(FumeshroomEntity object)
    {
        return new Identifier ("pvzmod", "animations/fumeshroom.json");
    }
}
