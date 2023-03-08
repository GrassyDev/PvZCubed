package io.github.GrassyDev.pvzmod.registry.entity.gravestones.futuregrave;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FutureGraveModel extends AnimatedGeoModel<FutureGraveEntity> {

    @Override
    public Identifier getModelResource(FutureGraveEntity object)
    {
        return new Identifier("pvzmod", "geo/futuregravestone.geo.json");
    }

    @Override
    public Identifier getTextureResource(FutureGraveEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/gravestone/futuregravestone.png");
    }

    @Override
    public Identifier getAnimationResource(FutureGraveEntity object)
    {
        return new Identifier ("pvzmod", "animations/gravestone.json");
    }
}
