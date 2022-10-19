package net.fabricmc.example.registry.plants.models;

import net.fabricmc.example.registry.plants.plantentity.PuffshroomEntity;
import net.fabricmc.example.registry.plants.plantentity.SunshroomEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SunshroomEntityModel extends AnimatedGeoModel<SunshroomEntity> {

    @Override
    public Identifier getModelLocation(SunshroomEntity object)
    {
        return new Identifier("pvzcubed", "geo/sunshroom.geo.json");
    }

    @Override
    public Identifier getTextureLocation(SunshroomEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/sunshroom/sunshroom.png");
    }

    @Override
    public Identifier getAnimationFileLocation(SunshroomEntity object)
    {
        return new Identifier ("pvzcubed", "animations/sunshroom.json");
    }
}