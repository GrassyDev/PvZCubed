package net.fabricmc.example.registry.plants.models;

import net.fabricmc.example.registry.plants.plantentity.SnowpeaEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SnowpeaEntityModel extends AnimatedGeoModel<SnowpeaEntity> {

    @Override
    public Identifier getModelLocation(SnowpeaEntity object)
    {
        return new Identifier("pvzcubed", "geo/snowpea.geo.json");
    }

    @Override
    public Identifier getTextureLocation(SnowpeaEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/peashooter/snowpea.png");
    }

    @Override
    public Identifier getAnimationFileLocation(SnowpeaEntity object)
    {
        return new Identifier ("pvzcubed", "animations/peashooter.json");
    }
}