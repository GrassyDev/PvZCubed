package net.fabricmc.example.registry.plants.models;

import net.fabricmc.example.registry.plants.plantentity.PeashooterEntity;
import net.fabricmc.example.registry.plants.plantentity.ThreepeaterEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ThreepeaterEntityModel extends AnimatedGeoModel<ThreepeaterEntity> {

    @Override
    public Identifier getModelLocation(ThreepeaterEntity object)
    {
        return new Identifier("pvzcubed", "geo/threepeater.geo.json");
    }

    @Override
    public Identifier getTextureLocation(ThreepeaterEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/peashooter/peashooter.png");
    }

    @Override
    public Identifier getAnimationFileLocation(ThreepeaterEntity object)
    {
        return new Identifier ("pvzcubed", "animations/threepeater.json");
    }
}