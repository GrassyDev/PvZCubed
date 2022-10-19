package io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.models;

import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoScreendoorEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoScreendoorEntityModel extends AnimatedGeoModel<HypnoScreendoorEntity> {

    @Override
    public Identifier getModelResource(HypnoScreendoorEntity object)
    {
        return new Identifier("pvzcubed", "geo/screendoor.geo.json");
    }

    @Override
    public Identifier getTextureResource(HypnoScreendoorEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/browncoat/screendoor_hypnotized.png");
    }

    @Override
    public Identifier getAnimationResource(HypnoScreendoorEntity object)
    {
        return new Identifier ("pvzcubed", "animations/screendoor.json");
    }
}
