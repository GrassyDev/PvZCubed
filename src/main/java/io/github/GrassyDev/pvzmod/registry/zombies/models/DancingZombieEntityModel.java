package io.github.GrassyDev.pvzmod.registry.zombies.models;

import io.github.GrassyDev.pvzmod.registry.zombies.zombieentity.DancingZombieEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DancingZombieEntityModel extends AnimatedGeoModel<DancingZombieEntity> {

    @Override
    public Identifier getModelResource(DancingZombieEntity object)
    {
        return new Identifier("pvzcubed", "geo/dancingzombie.geo.json");
    }

    @Override
    public Identifier getTextureResource(DancingZombieEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/dancingzombie/dancingzombie.png");
    }

    @Override
    public Identifier getAnimationResource(DancingZombieEntity object)
    {
        return new Identifier ("pvzcubed", "animations/dancingzombie.json");
    }
}