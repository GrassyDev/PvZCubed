package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.newspaper;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NewspaperEntityModel extends AnimatedGeoModel<NewspaperEntity> {

    @Override
    public Identifier getModelResource(NewspaperEntity object)
    {
        return new Identifier("pvzmod", "geo/newspaper.geo.json");
    }

    @Override
    public Identifier getTextureResource(NewspaperEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/newspaper/newspaper.png");
    }

    @Override
    public Identifier getAnimationResource(NewspaperEntity object)
    {
        return new Identifier ("pvzmod", "animations/newspaper.json");
    }
}
