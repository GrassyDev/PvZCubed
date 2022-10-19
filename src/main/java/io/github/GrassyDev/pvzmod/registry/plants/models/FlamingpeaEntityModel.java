package io.github.GrassyDev.pvzmod.registry.plants.models;

import io.github.GrassyDev.pvzmod.registry.plants.plantentity.FlamingpeaEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FlamingpeaEntityModel extends AnimatedGeoModel<FlamingpeaEntity> {

    @Override
    public Identifier getModelResource(FlamingpeaEntity object)
    {
        return new Identifier("pvzcubed", "geo/flamingpea.geo.json");
    }

    @Override
    public Identifier getTextureResource(FlamingpeaEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/peashooter/flamingpea.png");
    }

    @Override
    public Identifier getAnimationResource(FlamingpeaEntity object)
    {
        return new Identifier ("pvzcubed", "animations/peashooter.json");
    }
}