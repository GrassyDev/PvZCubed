package net.fabricmc.example.registry.zombies.models;

import net.fabricmc.example.registry.zombies.zombieentity.BackupDancerEntity;
import net.fabricmc.example.registry.zombies.zombieentity.DancingZombieEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BackupDancerEntityModel extends AnimatedGeoModel<BackupDancerEntity> {

    @Override
    public Identifier getModelLocation(BackupDancerEntity object)
    {
        return new Identifier("pvzcubed", "geo/backupdancer.geo.json");
    }

    @Override
    public Identifier getTextureLocation(BackupDancerEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/dancingzombie/backupdancer.png");
    }

    @Override
    public Identifier getAnimationFileLocation(BackupDancerEntity object)
    {
        return new Identifier ("pvzcubed", "animations/backupdancer.json");
    }
}