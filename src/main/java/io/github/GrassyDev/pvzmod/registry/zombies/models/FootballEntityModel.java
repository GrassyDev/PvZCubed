package net.fabricmc.example.registry.zombies.models;

import net.fabricmc.example.registry.zombies.zombieentity.BucketheadEntity;
import net.fabricmc.example.registry.zombies.zombieentity.FootballEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class FootballEntityModel extends AnimatedGeoModel<FootballEntity> {

    @Override
    public Identifier getModelLocation(FootballEntity object)
    {
        return new Identifier("pvzcubed", "geo/football.geo.json");
    }

    @Override
    public Identifier getTextureLocation(FootballEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/football/football.png");
    }

    @Override
    public Identifier getAnimationFileLocation(FootballEntity object)
    {
        return new Identifier ("pvzcubed", "animations/football.json");
    }
}