package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SunflowerEntityModel extends AnimatedGeoModel<SunflowerEntity> {

    @Override
    public Identifier getModelResource(SunflowerEntity object)
    {
        return new Identifier("pvzmod", "geo/sunflower.geo.json");
    }

    @Override
    public Identifier getTextureResource(SunflowerEntity object)
    {
		return SunflowerEntityRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getAnimationResource(SunflowerEntity object)
    {
        return new Identifier ("pvzmod", "animations/sunflower.json");
    }
}
