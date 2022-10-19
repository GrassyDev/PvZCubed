package io.github.GrassyDev.pvzmod.registry.plants.models;

import io.github.GrassyDev.pvzmod.registry.plants.plantentity.PuffshroomEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PuffshroomEntityModel extends AnimatedGeoModel<PuffshroomEntity> {

    @Override
    public Identifier getModelResource(PuffshroomEntity object)
    {
        return new Identifier("pvzcubed", "geo/puffshroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(PuffshroomEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/puffshroom/puffshroom.png");
    }

    @Override
    public Identifier getAnimationResource(PuffshroomEntity object)
    {
        return new Identifier ("pvzcubed", "animations/puffshroom.json");
    }
}
