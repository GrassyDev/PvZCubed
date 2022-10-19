package io.github.GrassyDev.pvzmod.registry.plants.models;

import io.github.GrassyDev.pvzmod.registry.plants.plantentity.CherrybombEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CherrybombEntityModel extends AnimatedGeoModel<CherrybombEntity> {

    @Override
    public Identifier getModelResource(CherrybombEntity object)
    {
        return new Identifier("pvzcubed", "geo/cherrybomb.geo.json");
    }

    @Override
    public Identifier getTextureResource(CherrybombEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/cherrybomb/cherrybomb.png");
    }

    @Override
    public Identifier getAnimationResource(CherrybombEntity object)
    {
        return new Identifier ("pvzcubed", "animations/cherrybomb.json");
    }
}
