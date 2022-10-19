package net.fabricmc.example.registry.hypnotizedzombies.models;

import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoConeheadEntity;
import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoPoleVaultingEntity;
import net.fabricmc.example.registry.zombies.zombieentity.PoleVaultingEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoPoleVaultingEntityModel extends AnimatedGeoModel<HypnoPoleVaultingEntity> {

    @Override
    public Identifier getModelLocation(HypnoPoleVaultingEntity object)
    {
        return new Identifier("pvzcubed", "geo/polevaulting.geo.json");
    }

    @Override
    public Identifier getTextureLocation(HypnoPoleVaultingEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/browncoat/polevaulting_hypnotized.png");
    }

    @Override
    public Identifier getAnimationFileLocation(HypnoPoleVaultingEntity object)
    {
        return new Identifier ("pvzcubed", "animations/polevaulting.json");
    }
}