package io.github.GrassyDev.pvzmod.registry.zombies.models;

import io.github.GrassyDev.pvzmod.registry.zombies.zombieentity.FlagzombieEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FlagzombieEntityModel extends AnimatedGeoModel<FlagzombieEntity> {

    @Override
    public Identifier getModelResource(FlagzombieEntity object)
    {
        return new Identifier("pvzcubed", "geo/flagzombie.geo.json");
    }

    @Override
    public Identifier getTextureResource(FlagzombieEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/browncoat/flagzombie.png");
    }

    @Override
    public Identifier getAnimationResource(FlagzombieEntity object)
    {
        return new Identifier ("pvzcubed", "animations/flagzombie.json");
    }
}