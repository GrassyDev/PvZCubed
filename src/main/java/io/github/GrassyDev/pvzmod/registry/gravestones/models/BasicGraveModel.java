package io.github.GrassyDev.pvzmod.registry.gravestones.models;

import io.github.GrassyDev.pvzmod.registry.gravestones.gravestoneentity.BasicGraveEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BasicGraveModel extends AnimatedGeoModel<BasicGraveEntity> {

    @Override
    public Identifier getModelResource(BasicGraveEntity object)
    {
        return new Identifier("pvzmod", "geo/basicgravestone.geo.json");
    }

    @Override
    public Identifier getTextureResource(BasicGraveEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/gravestone/basicgravestone.png");
    }

    @Override
    public Identifier getAnimationResource(BasicGraveEntity object)
    {
        return new Identifier ("pvzmod", "animations/gravestone.json");
    }
}
