package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.dandelionweed;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DandelionWeedEntityModel extends AnimatedGeoModel<DandelionWeedEntity> {

    @Override
    public Identifier getModelResource(DandelionWeedEntity object)
    {
        return new Identifier("pvzmod", "geo/dandelionweed.geo.json");
    }

    @Override
    public Identifier getTextureResource(DandelionWeedEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/dandelion/dandelionweed.png");
    }

    @Override
    public Identifier getAnimationResource(DandelionWeedEntity object)
    {
        return new Identifier ("pvzmod", "animations/dandelionweed.json");
    }
}
