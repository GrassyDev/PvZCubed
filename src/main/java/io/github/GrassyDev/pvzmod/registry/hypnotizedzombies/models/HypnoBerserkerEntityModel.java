package io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.models;

import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoBerserkerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class HypnoBerserkerEntityModel extends AnimatedGeoModel<HypnoBerserkerEntity> {

    @Override
    public Identifier getModelResource(HypnoBerserkerEntity object)
    {
        return new Identifier("pvzmod", "geo/berserker.geo.json");
    }

    @Override
    public Identifier getTextureResource(HypnoBerserkerEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/football/berserker_hypnotized.png");
    }

    @Override
    public Identifier getAnimationResource(HypnoBerserkerEntity object)
    {
        return new Identifier ("pvzmod", "animations/football.json");
    }
}
