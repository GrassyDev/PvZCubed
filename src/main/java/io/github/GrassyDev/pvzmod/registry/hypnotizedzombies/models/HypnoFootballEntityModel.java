package net.fabricmc.example.registry.hypnotizedzombies.models;

import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoFootballEntity;
import net.fabricmc.example.registry.zombies.zombieentity.FootballEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class HypnoFootballEntityModel extends AnimatedGeoModel<HypnoFootballEntity> {

    @Override
    public Identifier getModelLocation(HypnoFootballEntity object)
    {
        return new Identifier("pvzcubed", "geo/football.geo.json");
    }

    @Override
    public Identifier getTextureLocation(HypnoFootballEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/football/football_hypnotized.png");
    }

    @Override
    public Identifier getAnimationFileLocation(HypnoFootballEntity object)
    {
        return new Identifier ("pvzcubed", "animations/football.json");
    }
}