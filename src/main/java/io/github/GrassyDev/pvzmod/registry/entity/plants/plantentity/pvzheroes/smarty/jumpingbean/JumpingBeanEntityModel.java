package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smarty.jumpingbean;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class JumpingBeanEntityModel extends AnimatedGeoModel<JumpingBeanEntity> {

    @Override
    public Identifier getModelResource(JumpingBeanEntity object)
    {
        return new Identifier("pvzmod", "geo/jumpingbean.geo.json");
    }

    @Override
    public Identifier getTextureResource(JumpingBeanEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/springbean/jumpingbean.png");
    }

    @Override
    public Identifier getAnimationResource(JumpingBeanEntity object)
    {
        return new Identifier ("pvzmod", "animations/jumpingbean.json");
    }
}
