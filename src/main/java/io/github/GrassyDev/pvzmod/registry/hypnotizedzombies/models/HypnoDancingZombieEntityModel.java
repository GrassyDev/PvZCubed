package io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.models;

import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoDancingZombieEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoDancingZombieEntityModel extends AnimatedGeoModel<HypnoDancingZombieEntity> {

    @Override
    public Identifier getModelResource(HypnoDancingZombieEntity object)
    {
        return new Identifier("pvzcubed", "geo/dancingzombie.geo.json");
    }

    @Override
    public Identifier getTextureResource(HypnoDancingZombieEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/dancingzombie/dancingzombie_hypnotized.png");
    }

    @Override
    public Identifier getAnimationResource(HypnoDancingZombieEntity object)
    {
        return new Identifier ("pvzcubed", "animations/dancingzombie.json");
    }
}
