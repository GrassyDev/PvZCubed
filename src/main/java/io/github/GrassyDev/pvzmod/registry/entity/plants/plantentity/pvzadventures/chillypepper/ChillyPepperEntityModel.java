package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.chillypepper;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ChillyPepperEntityModel extends AnimatedGeoModel<ChillyPepperEntity> {

    @Override
    public Identifier getModelResource(ChillyPepperEntity object)
    {
        return new Identifier("pvzmod", "geo/chillypepper.geo.json");
    }

    @Override
    public Identifier getTextureResource(ChillyPepperEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/cherrybomb/chillypepper.png");
    }

    @Override
    public Identifier getAnimationResource(ChillyPepperEntity object)
    {
        return new Identifier ("pvzmod", "animations/jalapeno.json");
    }
}
