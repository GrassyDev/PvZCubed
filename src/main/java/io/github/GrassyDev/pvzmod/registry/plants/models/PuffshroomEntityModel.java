package net.fabricmc.example.registry.plants.models;

import net.fabricmc.example.registry.plants.plantentity.PuffshroomEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PuffshroomEntityModel extends AnimatedGeoModel<PuffshroomEntity> {

    @Override
    public Identifier getModelLocation(PuffshroomEntity object)
    {
        return new Identifier("pvzcubed", "geo/puffshroom.geo.json");
    }

    @Override
    public Identifier getTextureLocation(PuffshroomEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/puffshroom/puffshroom.png");
    }

    @Override
    public Identifier getAnimationFileLocation(PuffshroomEntity object)
    {
        return new Identifier ("pvzcubed", "animations/puffshroom.json");
    }
}