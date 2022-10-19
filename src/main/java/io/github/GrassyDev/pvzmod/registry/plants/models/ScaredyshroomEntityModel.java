package net.fabricmc.example.registry.plants.models;

import net.fabricmc.example.registry.plants.plantentity.PuffshroomEntity;
import net.fabricmc.example.registry.plants.plantentity.ScaredyshroomEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ScaredyshroomEntityModel extends AnimatedGeoModel<ScaredyshroomEntity> {

    @Override
    public Identifier getModelLocation(ScaredyshroomEntity object)
    {
        return new Identifier("pvzcubed", "geo/scaredyshroom.geo.json");
    }

    @Override
    public Identifier getTextureLocation(ScaredyshroomEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/puffshroom/scaredyshroom.png");
    }

    @Override
    public Identifier getAnimationFileLocation(ScaredyshroomEntity object)
    {
        return new Identifier ("pvzcubed", "animations/scaredyshroom.json");
    }
}