package io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.models;

import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoConeheadEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoConeheadEntityModel extends AnimatedGeoModel<HypnoConeheadEntity> {

    @Override
    public Identifier getModelResource(HypnoConeheadEntity object)
    {
        return new Identifier("pvzcubed", "geo/conehead.geo.json");
    }

    @Override
    public Identifier getTextureResource(HypnoConeheadEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/browncoat/conehead_hypnotized.png");
    }

    @Override
    public Identifier getAnimationResource(HypnoConeheadEntity object)
    {
        return new Identifier ("pvzcubed", "animations/newbrowncoat.json");
    }
}
