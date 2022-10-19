package io.github.GrassyDev.pvzmod.registry.plants.models;

import io.github.GrassyDev.pvzmod.registry.plants.plantentity.ScaredyshroomEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ScaredyshroomEntityModel extends AnimatedGeoModel<ScaredyshroomEntity> {

    @Override
    public Identifier getModelResource(ScaredyshroomEntity object)
    {
        return new Identifier("pvzcubed", "geo/scaredyshroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(ScaredyshroomEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/puffshroom/scaredyshroom.png");
    }

    @Override
    public Identifier getAnimationResource(ScaredyshroomEntity object)
    {
        return new Identifier ("pvzcubed", "animations/scaredyshroom.json");
    }
}
