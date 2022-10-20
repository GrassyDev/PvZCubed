package io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.models;

import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoFlagzombieEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoFlagzombieEntityModel extends AnimatedGeoModel<HypnoFlagzombieEntity> {

    @Override
    public Identifier getModelResource(HypnoFlagzombieEntity object)
    {
        return new Identifier("pvzmod", "geo/flagzombie.geo.json");
    }

    @Override
    public Identifier getTextureResource(HypnoFlagzombieEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/browncoat/flagzombie_hypnotized.png");
    }

    @Override
    public Identifier getAnimationResource(HypnoFlagzombieEntity object)
    {
        return new Identifier ("pvzmod", "animations/flagzombie.json");
    }
}
