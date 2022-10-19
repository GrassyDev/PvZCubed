package net.fabricmc.example.registry.hypnotizedzombies.models;

import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoBrowncoatEntity;
import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoFlagzombieEntity;
import net.fabricmc.example.registry.zombies.zombieentity.FlagzombieEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoFlagzombieEntityModel extends AnimatedGeoModel<HypnoFlagzombieEntity> {

    @Override
    public Identifier getModelLocation(HypnoFlagzombieEntity object)
    {
        return new Identifier("pvzcubed", "geo/flagzombie.geo.json");
    }

    @Override
    public Identifier getTextureLocation(HypnoFlagzombieEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/browncoat/flagzombie_hypnotized.png");
    }

    @Override
    public Identifier getAnimationFileLocation(HypnoFlagzombieEntity object)
    {
        return new Identifier ("pvzcubed", "animations/flagzombie.json");
    }
}