package net.fabricmc.example.registry.hypnotizedzombies.models;

import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoBerserkerEntity;
import net.fabricmc.example.registry.zombies.zombieentity.BerserkerEntity;
import net.fabricmc.example.registry.zombies.zombieentity.FootballEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class HypnoBerserkerEntityModel extends AnimatedGeoModel<HypnoBerserkerEntity> {

    @Override
    public Identifier getModelLocation(HypnoBerserkerEntity object)
    {
        return new Identifier("pvzcubed", "geo/berserker.geo.json");
    }

    @Override
    public Identifier getTextureLocation(HypnoBerserkerEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/football/berserker_hypnotized.png");
    }

    @Override
    public Identifier getAnimationFileLocation(HypnoBerserkerEntity object)
    {
        return new Identifier ("pvzcubed", "animations/football.json");
    }
}