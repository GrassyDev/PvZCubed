package net.fabricmc.example.registry.plants.models;

import net.fabricmc.example.registry.plants.plantentity.ChomperEntity;
import net.fabricmc.example.registry.plants.plantentity.PeashooterEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ChomperEntityModel extends AnimatedGeoModel<ChomperEntity> {

    @Override
    public Identifier getModelLocation(ChomperEntity object)
    {
        return new Identifier("pvzcubed", "geo/chomper.geo.json");
    }

    @Override
    public Identifier getTextureLocation(ChomperEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/chomper/chomper.png");
    }

    @Override
    public Identifier getAnimationFileLocation(ChomperEntity object)
    {
        return new Identifier ("pvzcubed", "animations/chomper.json");
    }
}