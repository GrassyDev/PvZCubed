package io.github.GrassyDev.pvzmod.registry.zombies.models;

import io.github.GrassyDev.pvzmod.registry.zombies.zombieentity.FootballEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class FootballEntityModel extends AnimatedGeoModel<FootballEntity> {

    @Override
    public Identifier getModelResource(FootballEntity object)
    {
        return new Identifier("pvzcubed", "geo/football.geo.json");
    }

    @Override
    public Identifier getTextureResource(FootballEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/football/football.png");
    }

    @Override
    public Identifier getAnimationResource(FootballEntity object)
    {
        return new Identifier ("pvzcubed", "animations/football.json");
    }
}
