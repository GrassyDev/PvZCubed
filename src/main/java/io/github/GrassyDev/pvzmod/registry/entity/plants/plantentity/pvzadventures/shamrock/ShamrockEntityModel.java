package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.shamrock;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShamrockEntityModel extends AnimatedGeoModel<ShamrockEntity> {

    @Override
    public Identifier getModelResource(ShamrockEntity object)
    {
        return new Identifier("pvzmod", "geo/shamrock.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShamrockEntity object)
    {
		return ShamrockEntityRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getAnimationResource(ShamrockEntity object)
    {
        return new Identifier ("pvzmod", "animations/shamrock.json");
    }
}
