package net.fabricmc.example.registry.plants.models;

import net.fabricmc.example.registry.plants.plantentity.RepeaterEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RepeaterEntityModel extends AnimatedGeoModel<RepeaterEntity> {

    @Override
    public Identifier getModelLocation(RepeaterEntity object)
    {
        return new Identifier("pvzcubed", "geo/repeater.geo.json");
    }

    @Override
    public Identifier getTextureLocation(RepeaterEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/peashooter/peashooter.png");
    }

    @Override
    public Identifier getAnimationFileLocation(RepeaterEntity object)
    {
        return new Identifier ("pvzcubed", "animations/peashooter.json");
    }
}