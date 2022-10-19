package io.github.GrassyDev.pvzmod.registry.zombies.models;

import io.github.GrassyDev.pvzmod.registry.zombies.zombieentity.NewspaperEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NewspaperEntityModel extends AnimatedGeoModel<NewspaperEntity> {

    @Override
    public Identifier getModelResource(NewspaperEntity object)
    {
        return new Identifier("pvzcubed", "geo/newspaper.geo.json");
    }

    @Override
    public Identifier getTextureResource(NewspaperEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/newspaper/newspaper.png");
    }

    @Override
    public Identifier getAnimationResource(NewspaperEntity object)
    {
        return new Identifier ("pvzcubed", "animations/newspaper.json");
    }
}
