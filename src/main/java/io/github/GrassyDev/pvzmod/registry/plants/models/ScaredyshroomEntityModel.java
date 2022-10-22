package io.github.GrassyDev.pvzmod.registry.plants.models;

import io.github.GrassyDev.pvzmod.registry.plants.plantentity.ScaredyshroomEntity;
import io.github.GrassyDev.pvzmod.registry.plants.renderers.FumeshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.plants.renderers.ScaredyshroomEntityRenderer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ScaredyshroomEntityModel extends AnimatedGeoModel<ScaredyshroomEntity> {

    @Override
    public Identifier getModelResource(ScaredyshroomEntity object)
    {
        return new Identifier("pvzmod", "geo/scaredyshroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(ScaredyshroomEntity object)
    {
		return ScaredyshroomEntityRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getAnimationResource(ScaredyshroomEntity object)
    {
        return new Identifier ("pvzmod", "animations/scaredyshroom.json");
    }
}
