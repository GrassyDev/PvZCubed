package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.snowpea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SnowpeaEntityModel extends AnimatedGeoModel<SnowpeaEntity> {

    @Override
    public Identifier getModelResource(SnowpeaEntity object)
    {
        return new Identifier("pvzmod", "geo/snowpea.geo.json");
    }

	public Identifier getTextureResource(SnowpeaEntity object) {
		return SnowpeaEntityRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
	}

    @Override
    public Identifier getAnimationResource(SnowpeaEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashooter.json");
    }
}
