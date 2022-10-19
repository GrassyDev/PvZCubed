package net.fabricmc.example.registry.zombies.models;

import net.fabricmc.example.registry.zombies.zombieentity.BrowncoatEntity;
import net.fabricmc.example.registry.zombies.zombieentity.FlagzombieEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FlagzombieEntityModel extends AnimatedGeoModel<FlagzombieEntity> {

    @Override
    public Identifier getModelLocation(FlagzombieEntity object)
    {
        return new Identifier("pvzcubed", "geo/flagzombie.geo.json");
    }

    @Override
    public Identifier getTextureLocation(FlagzombieEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/browncoat/flagzombie.png");
    }

    @Override
    public Identifier getAnimationFileLocation(FlagzombieEntity object)
    {
        return new Identifier ("pvzcubed", "animations/flagzombie.json");
    }
}