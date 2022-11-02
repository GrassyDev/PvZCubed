package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.models;

import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.HypnoDancingZombieEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoDancingZombieEntityModel extends AnimatedGeoModel<HypnoDancingZombieEntity> {

    @Override
    public Identifier getModelResource(HypnoDancingZombieEntity object)
    {
        return new Identifier("pvzmod", "geo/dancingzombie.geo.json");
    }

    @Override
    public Identifier getTextureResource(HypnoDancingZombieEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/dancingzombie/dancingzombie_hypnotized.png");
    }

    @Override
    public Identifier getAnimationResource(HypnoDancingZombieEntity object)
    {
        return new Identifier ("pvzmod", "animations/dancingzombie.json");
    }
}
