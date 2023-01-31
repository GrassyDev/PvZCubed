package io.github.GrassyDev.pvzmod.registry.entity.gravestones.gravestoneentity.poolgrave;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PoolGraveModel extends AnimatedGeoModel<PoolGraveEntity> {

    @Override
    public Identifier getModelResource(PoolGraveEntity object)
    {
        return new Identifier("pvzmod", "geo/poolgravestone.geo.json");
    }

    @Override
    public Identifier getTextureResource(PoolGraveEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/gravestone/basicgravestone.png");
    }

    @Override
    public Identifier getAnimationResource(PoolGraveEntity object)
    {
        return new Identifier ("pvzmod", "animations/gravestone.json");
    }
}
