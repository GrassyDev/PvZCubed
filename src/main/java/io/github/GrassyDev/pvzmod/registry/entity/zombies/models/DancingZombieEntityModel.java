package io.github.GrassyDev.pvzmod.registry.entity.zombies.models;

import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.DancingZombieEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DancingZombieEntityModel extends AnimatedGeoModel<DancingZombieEntity> {

    @Override
    public Identifier getModelResource(DancingZombieEntity object)
    {
        return new Identifier("pvzmod", "geo/dancingzombie.geo.json");
    }

    @Override
    public Identifier getTextureResource(DancingZombieEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/dancingzombie/dancingzombie.png");
    }

    @Override
    public Identifier getAnimationResource(DancingZombieEntity object)
    {
        return new Identifier ("pvzmod", "animations/dancingzombie.json");
    }
}
