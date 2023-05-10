package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.pirateseas.springbean;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SpringbeanEntityModel extends AnimatedGeoModel<SpringbeanEntity> {

    @Override
    public Identifier getModelResource(SpringbeanEntity object)
    {
        return new Identifier("pvzmod", "geo/springbean.geo.json");
    }

    @Override
    public Identifier getTextureResource(SpringbeanEntity object)
    {
		return new Identifier("pvzmod", "textures/entity/springbean/springbean.png");
    }

    @Override
    public Identifier getAnimationResource(SpringbeanEntity object)
    {
        return new Identifier ("pvzmod", "animations/springbean.json");
    }
}
