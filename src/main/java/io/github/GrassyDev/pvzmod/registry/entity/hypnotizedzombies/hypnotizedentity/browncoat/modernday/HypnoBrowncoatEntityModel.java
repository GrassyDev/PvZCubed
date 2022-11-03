package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.browncoat.modernday;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoBrowncoatEntityModel extends AnimatedGeoModel<HypnoBrowncoatEntity> {

    @Override
    public Identifier getModelResource(HypnoBrowncoatEntity object)
    {
        return new Identifier("pvzmod", "geo/browncoat.geo.json");
    }

    @Override
    public Identifier getTextureResource(HypnoBrowncoatEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/browncoat/browncoat_hypnotized.png");
    }

    @Override
    public Identifier getAnimationResource(HypnoBrowncoatEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
