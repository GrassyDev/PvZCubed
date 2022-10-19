package net.fabricmc.example.registry.plants.models;

import net.fabricmc.example.registry.plants.plantentity.GravebusterEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GravebusterEntityModel extends AnimatedGeoModel<GravebusterEntity> {

    @Override
    public Identifier getModelLocation(GravebusterEntity object)
    {
        return new Identifier("pvzcubed", "geo/gravebuster.geo.json");
    }

    @Override
    public Identifier getTextureLocation(GravebusterEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/gravebuster/gravebuster.png");
    }

    @Override
    public Identifier getAnimationFileLocation(GravebusterEntity object)
    {
        return new Identifier ("pvzcubed", "animations/gravebuster.json");
    }
}