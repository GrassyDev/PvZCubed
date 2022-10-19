package io.github.GrassyDev.pvzmod.registry.plants.models;

import io.github.GrassyDev.pvzmod.registry.plants.plantentity.PeashooterEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PeashooterEntityModel extends AnimatedGeoModel<PeashooterEntity> {

    @Override
    public Identifier getModelResource(PeashooterEntity object)
    {
        return new Identifier("pvzcubed", "geo/peashooter.geo.json");
    }

    @Override
    public Identifier getTextureResource(PeashooterEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/peashooter/peashooter.png");
    }

    @Override
    public Identifier getAnimationResource(PeashooterEntity object)
    {
        return new Identifier ("pvzcubed", "animations/peashooter.json");
    }
}
