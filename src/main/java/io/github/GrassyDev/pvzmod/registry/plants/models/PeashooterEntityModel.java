package net.fabricmc.example.registry.plants.models;

import net.fabricmc.example.registry.plants.plantentity.PeashooterEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PeashooterEntityModel extends AnimatedGeoModel<PeashooterEntity> {

    @Override
    public Identifier getModelLocation(PeashooterEntity object)
    {
        return new Identifier("pvzcubed", "geo/peashooter.geo.json");
    }

    @Override
    public Identifier getTextureLocation(PeashooterEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/peashooter/peashooter.png");
    }

    @Override
    public Identifier getAnimationFileLocation(PeashooterEntity object)
    {
        return new Identifier ("pvzcubed", "animations/peashooter.json");
    }
}