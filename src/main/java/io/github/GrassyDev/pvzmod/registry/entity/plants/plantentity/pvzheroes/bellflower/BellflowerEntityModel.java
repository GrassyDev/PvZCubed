package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.bellflower;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BellflowerEntityModel extends AnimatedGeoModel<BellflowerEntity> {

    @Override
    public Identifier getModelResource(BellflowerEntity object)
    {
        return new Identifier("pvzmod", "geo/bellflower.geo.json");
    }

    @Override
    public Identifier getTextureResource(BellflowerEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/bellflower/bellflower.png");
    }

    @Override
    public Identifier getAnimationResource(BellflowerEntity object)
    {
        return new Identifier ("pvzmod", "animations/bellflower.json");
    }
}
