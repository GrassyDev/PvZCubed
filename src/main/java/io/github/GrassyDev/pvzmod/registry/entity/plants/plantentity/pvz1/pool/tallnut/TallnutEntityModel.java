package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.tallnut;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TallnutEntityModel extends AnimatedGeoModel<TallnutEntity> {

    @Override
    public Identifier getModelResource(TallnutEntity object)
    {
        return new Identifier("pvzmod", "geo/tallnut.geo.json");
    }

    @Override
    public Identifier getTextureResource(TallnutEntity object)
    {
		return TallnutEntity.LOCATION_BY_VARIANT.get(object.getCrack());
    }

    @Override
    public Identifier getAnimationResource(TallnutEntity object)
    {
        return new Identifier ("pvzmod", "animations/wallnut.json");
    }
}
