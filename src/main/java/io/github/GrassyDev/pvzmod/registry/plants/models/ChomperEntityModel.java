package io.github.GrassyDev.pvzmod.registry.plants.models;

import io.github.GrassyDev.pvzmod.registry.plants.plantentity.ChomperEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ChomperEntityModel extends AnimatedGeoModel<ChomperEntity> {

    @Override
    public Identifier getModelResource(ChomperEntity object)
    {
        return new Identifier("pvzcubed", "geo/chomper.geo.json");
    }

    @Override
    public Identifier getTextureResource(ChomperEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/chomper/chomper.png");
    }

    @Override
    public Identifier getAnimationResource(ChomperEntity object)
    {
        return new Identifier ("pvzcubed", "animations/chomper.json");
    }
}
