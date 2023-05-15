package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.magicshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MagichatEntityModel extends AnimatedGeoModel<MagichatEntity> {

    @Override
    public Identifier getModelResource(MagichatEntity object)
    {
        return new Identifier("pvzmod", "geo/magichat.geo.json");
    }

    @Override
    public Identifier getTextureResource(MagichatEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/magicshroom/magicshroom.png");
    }

    @Override
    public Identifier getAnimationResource(MagichatEntity object)
    {
        return new Identifier ("pvzmod", "animations/magicshroom.json");
    }
}
