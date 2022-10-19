package io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.models;

import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoBrowncoatEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoBrowncoatEntityModel extends AnimatedGeoModel<HypnoBrowncoatEntity> {

    @Override
    public Identifier getModelResource(HypnoBrowncoatEntity object)
    {
        return new Identifier("pvzcubed", "geo/browncoat.geo.json");
    }

    @Override
    public Identifier getTextureResource(HypnoBrowncoatEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/browncoat/browncoat_hypnotized.png");
    }

    @Override
    public Identifier getAnimationResource(HypnoBrowncoatEntity object)
    {
        return new Identifier ("pvzcubed", "animations/newbrowncoat.json");
    }
}
