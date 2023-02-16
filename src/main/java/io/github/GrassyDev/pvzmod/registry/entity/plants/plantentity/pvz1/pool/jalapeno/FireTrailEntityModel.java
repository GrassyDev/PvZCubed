package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.jalapeno;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FireTrailEntityModel extends AnimatedGeoModel<FireTrailEntity> {

    @Override
    public Identifier getModelResource(FireTrailEntity object)
    {
        return new Identifier("pvzmod", "geo/firetrail.geo.json");
    }

    @Override
    public Identifier getTextureResource(FireTrailEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/cherrybomb/firetrail.png");
    }

    @Override
    public Identifier getAnimationResource(FireTrailEntity object)
    {
        return new Identifier ("pvzmod", "animations/firetrail.json");
    }
}
