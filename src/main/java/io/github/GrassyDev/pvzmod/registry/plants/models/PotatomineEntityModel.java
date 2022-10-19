package net.fabricmc.example.registry.plants.models;

import net.fabricmc.example.registry.plants.plantentity.CherrybombEntity;
import net.fabricmc.example.registry.plants.plantentity.PotatomineEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PotatomineEntityModel extends AnimatedGeoModel<PotatomineEntity> {

    @Override
    public Identifier getModelLocation(PotatomineEntity object)
    {
        return new Identifier("pvzcubed", "geo/potatomine.geo.json");
    }

    @Override
    public Identifier getTextureLocation(PotatomineEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/potatomine/potatomine.png");
    }

    @Override
    public Identifier getAnimationFileLocation(PotatomineEntity object)
    {
        return new Identifier ("pvzcubed", "animations/potatomine.json");
    }
}