package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.ancientegypt.iceberglettuce;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class IcebergLettuceEntityModel extends AnimatedGeoModel<IcebergLettuceEntity> {

    @Override
    public Identifier getModelResource(IcebergLettuceEntity object)
    {
        return new Identifier("pvzmod", "geo/iceberglettuce.geo.json");
    }

    @Override
    public Identifier getTextureResource(IcebergLettuceEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/small/iceberglettuce.png");
    }

    @Override
    public Identifier getAnimationResource(IcebergLettuceEntity object)
    {
        return new Identifier ("pvzmod", "animations/small.json");
    }
}
