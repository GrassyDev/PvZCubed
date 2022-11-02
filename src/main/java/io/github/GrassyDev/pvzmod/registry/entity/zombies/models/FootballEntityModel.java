package io.github.GrassyDev.pvzmod.registry.entity.zombies.models;

import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.FootballEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class FootballEntityModel extends AnimatedGeoModel<FootballEntity> {

    @Override
    public Identifier getModelResource(FootballEntity object)
    {
        return new Identifier("pvzmod", "geo/football.geo.json");
    }

    @Override
    public Identifier getTextureResource(FootballEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/football/football.png");
    }

    @Override
    public Identifier getAnimationResource(FootballEntity object)
    {
        return new Identifier ("pvzmod", "animations/football.json");
    }
}
