package net.fabricmc.example.registry.plants.models;

import net.fabricmc.example.registry.plants.plantentity.HypnoshroomEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoshroomEntityModel extends AnimatedGeoModel<HypnoshroomEntity> {

    @Override
    public Identifier getModelLocation(HypnoshroomEntity object)
    {
        return new Identifier("pvzcubed", "geo/hypnoshroom.geo.json");
    }

    @Override
    public Identifier getTextureLocation(HypnoshroomEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/hypnoshroom/hypnoshroom.png");
    }

    @Override
    public Identifier getAnimationFileLocation(HypnoshroomEntity object)
    {
        return new Identifier ("pvzcubed", "animations/hypnoshroom.json");
    }
}