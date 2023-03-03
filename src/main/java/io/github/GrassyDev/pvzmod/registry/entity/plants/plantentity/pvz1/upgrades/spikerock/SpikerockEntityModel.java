package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.spikerock;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SpikerockEntityModel extends AnimatedGeoModel<SpikerockEntity> {

    @Override
    public Identifier getModelResource(SpikerockEntity object)
    {
        return new Identifier("pvzmod", "geo/spikerock.geo.json");
    }

    @Override
    public Identifier getTextureResource(SpikerockEntity object)
    {
		return SpikerockEntity.LOCATION_BY_VARIANT.get(object.getCrack());
    }

    @Override
    public Identifier getAnimationResource(SpikerockEntity object)
    {
        return new Identifier ("pvzmod", "animations/spikeweed.json");
    }
}
