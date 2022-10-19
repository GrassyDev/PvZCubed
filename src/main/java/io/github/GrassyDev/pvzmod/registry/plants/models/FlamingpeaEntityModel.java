package net.fabricmc.example.registry.plants.models;

import net.fabricmc.example.registry.plants.plantentity.FlamingpeaEntity;
import net.fabricmc.example.registry.plants.plantentity.PeashooterEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FlamingpeaEntityModel extends AnimatedGeoModel<FlamingpeaEntity> {

    @Override
    public Identifier getModelLocation(FlamingpeaEntity object)
    {
        return new Identifier("pvzcubed", "geo/flamingpea.geo.json");
    }

    @Override
    public Identifier getTextureLocation(FlamingpeaEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/peashooter/flamingpea.png");
    }

    @Override
    public Identifier getAnimationFileLocation(FlamingpeaEntity object)
    {
        return new Identifier ("pvzcubed", "animations/peashooter.json");
    }
}