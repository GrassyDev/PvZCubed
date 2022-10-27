package io.github.GrassyDev.pvzmod.registry.plants.plantentity.hypnoshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoshroomEntityModel extends AnimatedGeoModel<HypnoshroomEntity> {

    @Override
    public Identifier getModelResource(HypnoshroomEntity object)
    {
        return new Identifier("pvzmod", "geo/hypnoshroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(HypnoshroomEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/hypnoshroom/hypnoshroom.png");
    }

    @Override
    public Identifier getAnimationResource(HypnoshroomEntity object)
    {
        return new Identifier ("pvzmod", "animations/hypnoshroom.json");
    }
}
