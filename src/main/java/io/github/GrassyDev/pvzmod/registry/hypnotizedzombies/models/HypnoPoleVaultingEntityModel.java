package io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.models;

import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoPoleVaultingEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoPoleVaultingEntityModel extends AnimatedGeoModel<HypnoPoleVaultingEntity> {

    @Override
    public Identifier getModelResource(HypnoPoleVaultingEntity object)
    {
        return new Identifier("pvzcubed", "geo/polevaulting.geo.json");
    }

    @Override
    public Identifier getTextureResource(HypnoPoleVaultingEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/browncoat/polevaulting_hypnotized.png");
    }

    @Override
    public Identifier getAnimationResource(HypnoPoleVaultingEntity object)
    {
        return new Identifier ("pvzcubed", "animations/polevaulting.json");
    }
}
