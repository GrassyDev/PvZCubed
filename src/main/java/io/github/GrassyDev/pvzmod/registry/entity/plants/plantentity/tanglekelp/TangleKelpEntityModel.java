package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.tanglekelp;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TangleKelpEntityModel extends AnimatedGeoModel<TangleKelpEntity> {

    @Override
    public Identifier getModelResource(TangleKelpEntity object)
    {
        return new Identifier("pvzmod", "geo/tanglekelp.geo.json");
    }

    @Override
    public Identifier getTextureResource(TangleKelpEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/tanglekelp/tanglekelp.png");
    }

    @Override
    public Identifier getAnimationResource(TangleKelpEntity object)
    {
        return new Identifier ("pvzmod", "animations/tanglekelp.json");
    }
}
