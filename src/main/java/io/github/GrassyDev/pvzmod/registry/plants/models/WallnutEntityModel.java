package net.fabricmc.example.registry.plants.models;

import net.fabricmc.example.registry.plants.plantentity.WallnutEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WallnutEntityModel extends AnimatedGeoModel<WallnutEntity> {

    @Override
    public Identifier getModelLocation(WallnutEntity object)
    {
        return new Identifier("pvzcubed", "geo/wallnut.geo.json");
    }

    @Override
    public Identifier getTextureLocation(WallnutEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/wallnut/wallnut.png");
    }

    @Override
    public Identifier getAnimationFileLocation(WallnutEntity object)
    {
        return new Identifier ("pvzcubed", "animations/wallnut.json");
    }
}