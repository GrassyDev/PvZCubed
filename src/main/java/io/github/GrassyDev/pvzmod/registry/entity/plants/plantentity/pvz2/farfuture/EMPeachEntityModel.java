package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.farfuture;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EMPeachEntityModel extends AnimatedGeoModel<EMPeachEntity> {

    @Override
    public Identifier getModelResource(EMPeachEntity object)
    {
        return new Identifier("pvzmod", "geo/empeach.geo.json");
    }

    @Override
    public Identifier getTextureResource(EMPeachEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/empeach/empeach.png");
    }

    @Override
    public Identifier getAnimationResource(EMPeachEntity object)
    {
        return new Identifier ("pvzmod", "animations/empeach.json");
    }
}
