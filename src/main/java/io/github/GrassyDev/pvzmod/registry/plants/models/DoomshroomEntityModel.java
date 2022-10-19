package net.fabricmc.example.registry.plants.models;

import net.fabricmc.example.registry.plants.plantentity.CherrybombEntity;
import net.fabricmc.example.registry.plants.plantentity.DoomshroomEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DoomshroomEntityModel extends AnimatedGeoModel<DoomshroomEntity> {

    @Override
    public Identifier getModelLocation(DoomshroomEntity object)
    {
        return new Identifier("pvzcubed", "geo/doomshroom.geo.json");
    }

    @Override
    public Identifier getTextureLocation(DoomshroomEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/doomshroom/doomshroom.png");
    }

    @Override
    public Identifier getAnimationFileLocation(DoomshroomEntity object)
    {
        return new Identifier ("pvzcubed", "animations/doomshroom.json");
    }
}