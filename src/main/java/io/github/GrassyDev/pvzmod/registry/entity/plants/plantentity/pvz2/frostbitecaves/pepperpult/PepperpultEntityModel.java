package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.frostbitecaves.pepperpult;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PepperpultEntityModel extends AnimatedGeoModel<PepperpultEntity> {

    @Override
    public Identifier getModelResource(PepperpultEntity object)
    {
        return new Identifier("pvzmod", "geo/pepperpult.geo.json");
    }

    @Override
    public Identifier getTextureResource(PepperpultEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/cabbagepult/pepperpult.png");
    }

    @Override
    public Identifier getAnimationResource(PepperpultEntity object)
    {
        return new Identifier ("pvzmod", "animations/cabbagepult.json");
    }
}
