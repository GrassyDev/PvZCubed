package io.github.GrassyDev.pvzmod.registry.plants.models;

import io.github.GrassyDev.pvzmod.registry.plants.plantentity.SnowpeaEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SnowpeaEntityModel extends AnimatedGeoModel<SnowpeaEntity> {

    @Override
    public Identifier getModelResource(SnowpeaEntity object)
    {
        return new Identifier("pvzcubed", "geo/snowpea.geo.json");
    }

    @Override
    public Identifier getTextureResource(SnowpeaEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/peashooter/snowpea.png");
    }

    @Override
    public Identifier getAnimationResource(SnowpeaEntity object)
    {
        return new Identifier ("pvzcubed", "animations/peashooter.json");
    }
}
