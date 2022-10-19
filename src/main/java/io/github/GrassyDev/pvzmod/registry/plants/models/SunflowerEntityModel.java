package io.github.GrassyDev.pvzmod.registry.plants.models;

import io.github.GrassyDev.pvzmod.registry.plants.plantentity.SunflowerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SunflowerEntityModel extends AnimatedGeoModel<SunflowerEntity> {

    @Override
    public Identifier getModelResource(SunflowerEntity object)
    {
        return new Identifier("pvzcubed", "geo/sunflower.geo.json");
    }

    @Override
    public Identifier getTextureResource(SunflowerEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/sunflower/pvzsunflower.png");
    }

    @Override
    public Identifier getAnimationResource(SunflowerEntity object)
    {
        return new Identifier ("pvzcubed", "animations/sunflower.json");
    }
}