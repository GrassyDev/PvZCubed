package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.snorkel;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoSnorkelEntityModel extends AnimatedGeoModel<HypnoSnorkelEntity> {

    @Override
    public Identifier getModelResource(HypnoSnorkelEntity object)
    {
        return new Identifier("pvzmod", "geo/snorkel.geo.json");
    }

    @Override
    public Identifier getTextureResource(HypnoSnorkelEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/snorkel/snorkel_hypnotized.png");
    }

    @Override
    public Identifier getAnimationResource(HypnoSnorkelEntity object)
    {
        return new Identifier ("pvzmod", "animations/snorkel.json");
    }
}
