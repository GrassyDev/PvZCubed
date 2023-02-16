package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.jalapeno;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class JalapenoEntityModel extends AnimatedGeoModel<JalapenoEntity> {

    @Override
    public Identifier getModelResource(JalapenoEntity object)
    {
        return new Identifier("pvzmod", "geo/jalapeno.geo.json");
    }

    @Override
    public Identifier getTextureResource(JalapenoEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/cherrybomb/jalapeno.png");
    }

    @Override
    public Identifier getAnimationResource(JalapenoEntity object)
    {
        return new Identifier ("pvzmod", "animations/jalapeno.json");
    }
}
