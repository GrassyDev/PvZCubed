package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.twinsunflower;

import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.sunflower.SunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.sunflower.SunflowerEntityRenderer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TwinSunflowerEntityModel extends AnimatedGeoModel<TwinSunflowerEntity> {

    @Override
    public Identifier getModelResource(TwinSunflowerEntity object)
    {
        return new Identifier("pvzmod", "geo/twinsunflower.geo.json");
    }

    @Override
    public Identifier getTextureResource(TwinSunflowerEntity object)
    {
		return TwinSunflowerEntityRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getAnimationResource(TwinSunflowerEntity object)
    {
        return new Identifier ("pvzmod", "animations/sunflower.json");
    }
}
