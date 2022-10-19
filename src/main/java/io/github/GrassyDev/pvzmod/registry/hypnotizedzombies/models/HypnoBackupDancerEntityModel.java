package net.fabricmc.example.registry.hypnotizedzombies.models;

import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoBackupDancerEntity;
import net.fabricmc.example.registry.zombies.zombieentity.BackupDancerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoBackupDancerEntityModel extends AnimatedGeoModel<HypnoBackupDancerEntity> {

    @Override
    public Identifier getModelLocation(HypnoBackupDancerEntity object)
    {
        return new Identifier("pvzcubed", "geo/backupdancer.geo.json");
    }

    @Override
    public Identifier getTextureLocation(HypnoBackupDancerEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/dancingzombie/backupdancer_hypnotized.png");
    }

    @Override
    public Identifier getAnimationFileLocation(HypnoBackupDancerEntity object)
    {
        return new Identifier ("pvzcubed", "animations/backupdancer.json");
    }
}