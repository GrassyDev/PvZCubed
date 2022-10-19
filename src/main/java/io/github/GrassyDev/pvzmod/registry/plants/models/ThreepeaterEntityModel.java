package io.github.GrassyDev.pvzmod.registry.plants.models;

import io.github.GrassyDev.pvzmod.registry.plants.plantentity.ThreepeaterEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ThreepeaterEntityModel extends AnimatedGeoModel<ThreepeaterEntity> {

    @Override
    public Identifier getModelResource(ThreepeaterEntity object)
    {
        return new Identifier("pvzcubed", "geo/threepeater.geo.json");
    }

    @Override
    public Identifier getTextureResource(ThreepeaterEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/peashooter/peashooter.png");
    }

    @Override
    public Identifier getAnimationResource(ThreepeaterEntity object)
    {
        return new Identifier ("pvzcubed", "animations/threepeater.json");
    }
}
