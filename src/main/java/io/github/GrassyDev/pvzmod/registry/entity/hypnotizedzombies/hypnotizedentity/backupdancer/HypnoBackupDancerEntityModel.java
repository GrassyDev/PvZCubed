package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.backupdancer;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoBackupDancerEntityModel extends AnimatedGeoModel<HypnoBackupDancerEntity> {

    @Override
    public Identifier getModelResource(HypnoBackupDancerEntity object)
    {
        return new Identifier("pvzmod", "geo/backupdancer.geo.json");
    }

    @Override
    public Identifier getTextureResource(HypnoBackupDancerEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/dancingzombie/backupdancer_hypnotized.png");
    }

    @Override
    public Identifier getAnimationResource(HypnoBackupDancerEntity object)
    {
        return new Identifier ("pvzmod", "animations/backupdancer.json");
    }
}
