package net.fabricmc.example.registry.zombies.models;

import net.fabricmc.example.registry.zombies.zombieentity.BerserkerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class BerserkerEntityModel extends AnimatedGeoModel<BerserkerEntity> {

    @Override
    public Identifier getModelLocation(BerserkerEntity object)
    {
        return new Identifier("pvzcubed", "geo/berserker.geo.json");
    }

    @Override
    public Identifier getTextureLocation(BerserkerEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/football/berserker.png");
    }

    @Override
    public Identifier getAnimationFileLocation(BerserkerEntity object)
    {
        return new Identifier ("pvzcubed", "animations/football.json");
    }
}