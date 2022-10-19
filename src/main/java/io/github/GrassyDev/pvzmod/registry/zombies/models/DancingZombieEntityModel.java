package net.fabricmc.example.registry.zombies.models;

import net.fabricmc.example.registry.zombies.zombieentity.BrowncoatEntity;
import net.fabricmc.example.registry.zombies.zombieentity.DancingZombieEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DancingZombieEntityModel extends AnimatedGeoModel<DancingZombieEntity> {

    @Override
    public Identifier getModelLocation(DancingZombieEntity object)
    {
        return new Identifier("pvzcubed", "geo/dancingzombie.geo.json");
    }

    @Override
    public Identifier getTextureLocation(DancingZombieEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/dancingzombie/dancingzombie.png");
    }

    @Override
    public Identifier getAnimationFileLocation(DancingZombieEntity object)
    {
        return new Identifier ("pvzcubed", "animations/dancingzombie.json");
    }
}