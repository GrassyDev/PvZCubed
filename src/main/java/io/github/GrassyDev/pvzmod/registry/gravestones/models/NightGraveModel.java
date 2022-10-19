package io.github.GrassyDev.pvzmod.registry.gravestones.models;

import io.github.GrassyDev.pvzmod.registry.gravestones.gravestoneentity.NightGraveEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NightGraveModel extends AnimatedGeoModel<NightGraveEntity> {

    @Override
    public Identifier getModelResource(NightGraveEntity object)
    {
        return new Identifier("pvzcubed", "geo/nightgravestone.geo.json");
    }

    @Override
    public Identifier getTextureResource(NightGraveEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/gravestone/nightgravestone.png");
    }

    @Override
    public Identifier getAnimationResource(NightGraveEntity object)
    {
        return new Identifier ("pvzcubed", "animations/gravestone.json");
    }
}
