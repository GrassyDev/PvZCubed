package io.github.GrassyDev.pvzmod.registry.plants.models;

import io.github.GrassyDev.pvzmod.registry.plants.plantentity.GravebusterEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GravebusterEntityModel extends AnimatedGeoModel<GravebusterEntity> {

    @Override
    public Identifier getModelResource(GravebusterEntity object)
    {
        return new Identifier("pvzcubed", "geo/gravebuster.geo.json");
    }

    @Override
    public Identifier getTextureResource(GravebusterEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/gravebuster/gravebuster.png");
    }

    @Override
    public Identifier getAnimationResource(GravebusterEntity object)
    {
        return new Identifier ("pvzcubed", "animations/gravebuster.json");
    }
}