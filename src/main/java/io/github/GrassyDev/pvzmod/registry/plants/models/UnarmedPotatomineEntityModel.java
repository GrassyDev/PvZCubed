package io.github.GrassyDev.pvzmod.registry.plants.models;

import io.github.GrassyDev.pvzmod.registry.plants.plantentity.UnarmedPotatomineEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class UnarmedPotatomineEntityModel extends AnimatedGeoModel<UnarmedPotatomineEntity> {

    @Override
    public Identifier getModelResource(UnarmedPotatomineEntity object)
    {
        return new Identifier("pvzcubed", "geo/potatomine.geo.json");
    }

    @Override
    public Identifier getTextureResource(UnarmedPotatomineEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/potatomine/potatomine.png");
    }

    @Override
    public Identifier getAnimationResource(UnarmedPotatomineEntity object)
    {
        return new Identifier ("pvzcubed", "animations/potatomine.json");
    }
}
