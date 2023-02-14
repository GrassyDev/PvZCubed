package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.narcissus;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NarcissusEntityModel extends AnimatedGeoModel<NarcissusEntity> {

    @Override
    public Identifier getModelResource(NarcissusEntity object)
    {
        return new Identifier("pvzmod", "geo/narcissus.geo.json");
    }

    @Override
    public Identifier getTextureResource(NarcissusEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/narcissus/narcissus.png");
    }

    @Override
    public Identifier getAnimationResource(NarcissusEntity object)
    {
        return new Identifier ("pvzmod", "animations/narcissus.json");
    }
}
