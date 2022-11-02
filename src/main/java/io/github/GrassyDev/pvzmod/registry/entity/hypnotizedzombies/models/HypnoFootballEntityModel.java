package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.models;

import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.HypnoFootballEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class HypnoFootballEntityModel extends AnimatedGeoModel<HypnoFootballEntity> {

    @Override
    public Identifier getModelResource(HypnoFootballEntity object)
    {
        return new Identifier("pvzmod", "geo/football.geo.json");
    }

    @Override
    public Identifier getTextureResource(HypnoFootballEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/football/football_hypnotized.png");
    }

    @Override
    public Identifier getAnimationResource(HypnoFootballEntity object)
    {
        return new Identifier ("pvzmod", "animations/football.json");
    }
}
