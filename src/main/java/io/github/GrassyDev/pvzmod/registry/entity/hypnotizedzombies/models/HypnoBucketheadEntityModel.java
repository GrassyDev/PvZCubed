package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.models;

import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.HypnoBucketheadEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoBucketheadEntityModel extends AnimatedGeoModel<HypnoBucketheadEntity> {

    @Override
    public Identifier getModelResource(HypnoBucketheadEntity object)
    {
        return new Identifier("pvzmod", "geo/buckethead.geo.json");
    }

    @Override
    public Identifier getTextureResource(HypnoBucketheadEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/browncoat/buckethead_hypnotized.png");
    }

    @Override
    public Identifier getAnimationResource(HypnoBucketheadEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
