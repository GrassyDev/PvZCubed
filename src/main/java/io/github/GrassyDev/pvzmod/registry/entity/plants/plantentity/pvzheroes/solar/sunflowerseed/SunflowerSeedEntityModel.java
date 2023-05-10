package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.solar.sunflowerseed;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SunflowerSeedEntityModel extends AnimatedGeoModel<SunflowerSeedEntity> {

    @Override
    public Identifier getModelResource(SunflowerSeedEntity object)
    {
        return new Identifier("pvzmod", "geo/sunflowerseed.geo.json");
    }

    @Override
    public Identifier getTextureResource(SunflowerSeedEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/small/sunflowerseed.png");
    }

    @Override
    public Identifier getAnimationResource(SunflowerSeedEntity object)
    {
        return new Identifier ("pvzmod", "animations/small.json");
    }
}
