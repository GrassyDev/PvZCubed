package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.wildwest.peapod;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PeapodEntityModel extends AnimatedGeoModel<PeapodEntity> {

    @Override
    public Identifier getModelResource(PeapodEntity object)
    {
        return new Identifier("pvzmod", "geo/peapod.geo.json");
    }

    @Override
    public Identifier getTextureResource(PeapodEntity object){
		return PeapodEntityRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getAnimationResource(PeapodEntity object)
    {
        return new Identifier ("pvzmod", "animations/peapod.json");
    }
}
