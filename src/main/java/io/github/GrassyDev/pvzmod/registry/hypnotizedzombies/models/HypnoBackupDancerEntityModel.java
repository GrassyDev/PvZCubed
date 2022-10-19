package io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.models;

import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoBackupDancerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoBackupDancerEntityModel extends AnimatedGeoModel<HypnoBackupDancerEntity> {

    @Override
    public Identifier getModelResource(HypnoBackupDancerEntity object)
    {
        return new Identifier("pvzcubed", "geo/backupdancer.geo.json");
    }

    @Override
    public Identifier getTextureResource(HypnoBackupDancerEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/dancingzombie/backupdancer_hypnotized.png");
    }

    @Override
    public Identifier getAnimationResource(HypnoBackupDancerEntity object)
    {
        return new Identifier ("pvzcubed", "animations/backupdancer.json");
    }
}
