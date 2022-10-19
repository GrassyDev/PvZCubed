package io.github.GrassyDev.pvzmod.registry.zombies.models;

import io.github.GrassyDev.pvzmod.registry.zombies.zombieentity.BerserkerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class BerserkerEntityModel extends AnimatedGeoModel<BerserkerEntity> {

    @Override
    public Identifier getModelResource(BerserkerEntity object)
    {
        return new Identifier("pvzcubed", "geo/berserker.geo.json");
    }

    @Override
    public Identifier getTextureResource(BerserkerEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/football/berserker.png");
    }

    @Override
    public Identifier getAnimationResource(BerserkerEntity object)
    {
        return new Identifier ("pvzcubed", "animations/football.json");
    }
}
