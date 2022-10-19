package net.fabricmc.example.registry.plants.models;

import net.fabricmc.example.registry.plants.plantentity.FumeshroomEntity;
import net.fabricmc.example.registry.plants.plantentity.PuffshroomEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FumeshroomEntityModel extends AnimatedGeoModel<FumeshroomEntity> {

    @Override
    public Identifier getModelLocation(FumeshroomEntity object)
    {
        return new Identifier("pvzcubed", "geo/fumeshroom.geo.json");
    }

    @Override
    public Identifier getTextureLocation(FumeshroomEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/fumeshroom/fumeshroom.png");
    }

    @Override
    public Identifier getAnimationFileLocation(FumeshroomEntity object)
    {
        return new Identifier ("pvzcubed", "animations/fumeshroom.json");
    }
}