package net.fabricmc.example.registry.hypnotizedzombies.models;

import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoDancingZombieEntity;
import net.fabricmc.example.registry.zombies.zombieentity.DancingZombieEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoDancingZombieEntityModel extends AnimatedGeoModel<HypnoDancingZombieEntity> {

    @Override
    public Identifier getModelLocation(HypnoDancingZombieEntity object)
    {
        return new Identifier("pvzcubed", "geo/dancingzombie.geo.json");
    }

    @Override
    public Identifier getTextureLocation(HypnoDancingZombieEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/dancingzombie/dancingzombie_hypnotized.png");
    }

    @Override
    public Identifier getAnimationFileLocation(HypnoDancingZombieEntity object)
    {
        return new Identifier ("pvzcubed", "animations/dancingzombie.json");
    }
}