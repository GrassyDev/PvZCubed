package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.roof.cabbagepult;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CabbagepultEntityModel extends AnimatedGeoModel<CabbagepultEntity> {

    @Override
    public Identifier getModelResource(CabbagepultEntity object)
    {
        return new Identifier("pvzmod", "geo/cabbagepult.geo.json");
    }

    @Override
    public Identifier getTextureResource(CabbagepultEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/cabbagepult/cabbagepult.png");
    }

    @Override
    public Identifier getAnimationResource(CabbagepultEntity object)
    {
        return new Identifier ("pvzmod", "animations/cabbagepult.json");
    }
}
