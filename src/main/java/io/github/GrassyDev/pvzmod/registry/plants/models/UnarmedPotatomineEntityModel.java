package net.fabricmc.example.registry.plants.models;

import net.fabricmc.example.registry.plants.plantentity.PotatomineEntity;
import net.fabricmc.example.registry.plants.plantentity.UnarmedPotatomineEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class UnarmedPotatomineEntityModel extends AnimatedGeoModel<UnarmedPotatomineEntity> {

    @Override
    public Identifier getModelLocation(UnarmedPotatomineEntity object)
    {
        return new Identifier("pvzcubed", "geo/potatomine.geo.json");
    }

    @Override
    public Identifier getTextureLocation(UnarmedPotatomineEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/potatomine/potatomine.png");
    }

    @Override
    public Identifier getAnimationFileLocation(UnarmedPotatomineEntity object)
    {
        return new Identifier ("pvzcubed", "animations/potatomine.json");
    }
}