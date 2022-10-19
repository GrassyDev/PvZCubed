package net.fabricmc.example.registry.plants.models;

import net.fabricmc.example.registry.plants.plantentity.SunflowerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SunflowerEntityModel extends AnimatedGeoModel<SunflowerEntity> {

    @Override
    public Identifier getModelLocation(SunflowerEntity object)
    {
        return new Identifier("pvzcubed", "geo/sunflower.geo.json");
    }

    @Override
    public Identifier getTextureLocation(SunflowerEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/sunflower/pvzsunflower.png");
    }

    @Override
    public Identifier getAnimationFileLocation(SunflowerEntity object)
    {
        return new Identifier ("pvzcubed", "animations/sunflower.json");
    }
}