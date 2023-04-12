package io.github.GrassyDev.pvzmod.registry.entity.gravestones.egyptgravestone;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EgyptGraveModel extends AnimatedGeoModel<EgyptGraveEntity> {

    @Override
    public Identifier getModelResource(EgyptGraveEntity object)
    {
        return new Identifier("pvzmod", "geo/egyptgravestone.geo.json");
    }

    @Override
    public Identifier getTextureResource(EgyptGraveEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/gravestone/egyptgravestone.png");
    }

    @Override
    public Identifier getAnimationResource(EgyptGraveEntity object)
    {
        return new Identifier ("pvzmod", "animations/gravestone.json");
    }
}
