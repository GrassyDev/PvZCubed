package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.conehead.modernday;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoConeheadEntityModel extends AnimatedGeoModel<HypnoConeheadEntity> {

    @Override
    public Identifier getModelResource(HypnoConeheadEntity object)
    {
        return new Identifier("pvzmod", "geo/conehead.geo.json");
    }

    @Override
    public Identifier getTextureResource(HypnoConeheadEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/browncoat/conehead_hypnotized.png");
    }

    @Override
    public Identifier getAnimationResource(HypnoConeheadEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
