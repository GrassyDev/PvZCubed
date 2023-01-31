package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.chomper;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ChomperEntityModel extends AnimatedGeoModel<ChomperEntity> {

    @Override
    public Identifier getModelResource(ChomperEntity object)
    {
        return new Identifier("pvzmod", "geo/chomper.geo.json");
    }

	public Identifier getTextureResource(ChomperEntity object) {
		return ChomperEntityRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
	}

    @Override
    public Identifier getAnimationResource(ChomperEntity object)
    {
        return new Identifier ("pvzmod", "animations/chomper.json");
    }
}
