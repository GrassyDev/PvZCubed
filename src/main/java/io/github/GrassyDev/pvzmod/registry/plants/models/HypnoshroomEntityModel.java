package io.github.GrassyDev.pvzmod.registry.plants.models;

import io.github.GrassyDev.pvzmod.registry.plants.plantentity.HypnoshroomEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoshroomEntityModel extends AnimatedGeoModel<HypnoshroomEntity> {

    @Override
    public Identifier getModelResource(HypnoshroomEntity object)
    {
        return new Identifier("pvzcubed", "geo/hypnoshroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(HypnoshroomEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/hypnoshroom/hypnoshroom.png");
    }

    @Override
    public Identifier getAnimationResource(HypnoshroomEntity object)
    {
        return new Identifier ("pvzcubed", "animations/hypnoshroom.json");
    }
}
