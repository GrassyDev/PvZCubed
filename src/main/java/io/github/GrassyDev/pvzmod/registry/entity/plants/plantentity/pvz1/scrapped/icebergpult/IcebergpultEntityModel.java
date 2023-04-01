package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.scrapped.icebergpult;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class IcebergpultEntityModel extends AnimatedGeoModel<IcebergpultEntity> {

    @Override
    public Identifier getModelResource(IcebergpultEntity object)
    {
        return new Identifier("pvzmod", "geo/icebergpult.geo.json");
    }

    @Override
    public Identifier getTextureResource(IcebergpultEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/cabbagepult/icebergpult.png");
    }

    @Override
    public Identifier getAnimationResource(IcebergpultEntity object)
    {
        return new Identifier ("pvzmod", "animations/cabbagepult.json");
    }
}
