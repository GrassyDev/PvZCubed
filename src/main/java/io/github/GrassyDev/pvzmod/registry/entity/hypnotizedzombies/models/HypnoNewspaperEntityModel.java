package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.models;

import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.HypnoNewspaperEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoNewspaperEntityModel extends AnimatedGeoModel<HypnoNewspaperEntity> {

    @Override
    public Identifier getModelResource(HypnoNewspaperEntity object)
    {
        return new Identifier("pvzmod", "geo/newspaper.geo.json");
    }

    @Override
    public Identifier getTextureResource(HypnoNewspaperEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/newspaper/newspaper_hypnotized.png");
    }

    @Override
    public Identifier getAnimationResource(HypnoNewspaperEntity object)
    {
        return new Identifier ("pvzmod", "animations/newspaper.json");
    }
}
