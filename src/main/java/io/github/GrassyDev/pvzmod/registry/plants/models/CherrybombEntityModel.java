package net.fabricmc.example.registry.plants.models;

import net.fabricmc.example.registry.plants.plantentity.CherrybombEntity;
import net.fabricmc.example.registry.plants.plantentity.PeashooterEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CherrybombEntityModel extends AnimatedGeoModel<CherrybombEntity> {

    @Override
    public Identifier getModelLocation(CherrybombEntity object)
    {
        return new Identifier("pvzcubed", "geo/cherrybomb.geo.json");
    }

    @Override
    public Identifier getTextureLocation(CherrybombEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/cherrybomb/cherrybomb.png");
    }

    @Override
    public Identifier getAnimationFileLocation(CherrybombEntity object)
    {
        return new Identifier ("pvzcubed", "animations/cherrybomb.json");
    }
}