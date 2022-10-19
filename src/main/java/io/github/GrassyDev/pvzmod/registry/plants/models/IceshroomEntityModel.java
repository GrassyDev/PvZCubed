package net.fabricmc.example.registry.plants.models;

import net.fabricmc.example.registry.plants.plantentity.DoomshroomEntity;
import net.fabricmc.example.registry.plants.plantentity.IceshroomEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class IceshroomEntityModel extends AnimatedGeoModel<IceshroomEntity> {

    @Override
    public Identifier getModelLocation(IceshroomEntity object)
    {
        return new Identifier("pvzcubed", "geo/iceshroom.geo.json");
    }

    @Override
    public Identifier getTextureLocation(IceshroomEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/iceshroom/iceshroom.png");
    }

    @Override
    public Identifier getAnimationFileLocation(IceshroomEntity object)
    {
        return new Identifier ("pvzcubed", "animations/iceshroom.json");
    }
}