package io.github.GrassyDev.pvzmod.registry.zombies.models;

import io.github.GrassyDev.pvzmod.registry.zombies.zombieentity.BackupDancerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BackupDancerEntityModel extends AnimatedGeoModel<BackupDancerEntity> {

    @Override
    public Identifier getModelResource(BackupDancerEntity object)
    {
        return new Identifier("pvzcubed", "geo/backupdancer.geo.json");
    }

    @Override
    public Identifier getTextureResource(BackupDancerEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/dancingzombie/backupdancer.png");
    }

    @Override
    public Identifier getAnimationResource(BackupDancerEntity object)
    {
        return new Identifier ("pvzcubed", "animations/backupdancer.json");
    }
}
