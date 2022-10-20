package io.github.GrassyDev.pvzmod.registry.plants.models;

import io.github.GrassyDev.pvzmod.registry.plants.plantentity.IceshroomEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class IceshroomEntityModel extends AnimatedGeoModel<IceshroomEntity> {

    @Override
    public Identifier getModelResource(IceshroomEntity object)
    {
        return new Identifier("pvzmod", "geo/iceshroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(IceshroomEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/iceshroom/iceshroom.png");
    }

    @Override
    public Identifier getAnimationResource(IceshroomEntity object)
    {
        return new Identifier ("pvzmod", "animations/iceshroom.json");
    }
}
