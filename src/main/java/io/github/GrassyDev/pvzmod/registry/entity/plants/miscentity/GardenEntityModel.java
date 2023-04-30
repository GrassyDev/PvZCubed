package io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GardenEntityModel extends AnimatedGeoModel<GardenEntity> {

    @Override
    public Identifier getModelResource(GardenEntity object)
    {
        return new Identifier("pvzmod", "geo/garden.geo.json");
    }

    @Override
    public Identifier getTextureResource(GardenEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/misc/garden.png");
    }

    @Override
    public Identifier getAnimationResource(GardenEntity object)
    {
        return new Identifier ("pvzmod", "animations/garden.json");
    }
}
