package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smarty.admiralnavybean;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AdmiralNavyBeanEntityModel extends AnimatedGeoModel<AdmiralNavyBeanEntity> {

    @Override
    public Identifier getModelResource(AdmiralNavyBeanEntity object)
    {
        return new Identifier("pvzmod", "geo/admiralnavybean.geo.json");
    }

	public Identifier getTextureResource(AdmiralNavyBeanEntity object) {
		return new Identifier("pvzmod", "textures/entity/bean/admiralnavybean.png");
	}

    @Override
    public Identifier getAnimationResource(AdmiralNavyBeanEntity object)
    {
        return new Identifier ("pvzmod", "animations/navybean.json");
    }
}
