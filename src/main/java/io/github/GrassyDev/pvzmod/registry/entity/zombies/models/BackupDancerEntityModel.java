package io.github.GrassyDev.pvzmod.registry.entity.zombies.models;

import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.BackupDancerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BackupDancerEntityModel extends AnimatedGeoModel<BackupDancerEntity> {

    @Override
    public Identifier getModelResource(BackupDancerEntity object)
    {
        return new Identifier("pvzmod", "geo/backupdancer.geo.json");
    }

    @Override
    public Identifier getTextureResource(BackupDancerEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/dancingzombie/backupdancer.png");
    }

    @Override
    public Identifier getAnimationResource(BackupDancerEntity object)
    {
        return new Identifier ("pvzmod", "animations/backupdancer.json");
    }
}
