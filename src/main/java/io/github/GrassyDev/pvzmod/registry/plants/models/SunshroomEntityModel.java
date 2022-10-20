package io.github.GrassyDev.pvzmod.registry.plants.models;

import io.github.GrassyDev.pvzmod.registry.plants.plantentity.SunshroomEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SunshroomEntityModel extends AnimatedGeoModel<SunshroomEntity> {

    @Override
    public Identifier getModelResource(SunshroomEntity object)
    {
        return new Identifier("pvzmod", "geo/sunshroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(SunshroomEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/sunshroom/sunshroom.png");
    }

    @Override
    public Identifier getAnimationResource(SunshroomEntity object)
    {
        return new Identifier ("pvzmod", "animations/sunshroom.json");
    }
}
