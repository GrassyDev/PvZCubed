package io.github.GrassyDev.pvzmod.registry.plants.models;

import io.github.GrassyDev.pvzmod.registry.plants.plantentity.PotatomineEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PotatomineEntityModel extends AnimatedGeoModel<PotatomineEntity> {

    @Override
    public Identifier getModelResource(PotatomineEntity object)
    {
        return new Identifier("pvzcubed", "geo/potatomine.geo.json");
    }

    @Override
    public Identifier getTextureResource(PotatomineEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/potatomine/potatomine.png");
    }

    @Override
    public Identifier getAnimationResource(PotatomineEntity object)
    {
        return new Identifier ("pvzcubed", "animations/potatomine.json");
    }
}
