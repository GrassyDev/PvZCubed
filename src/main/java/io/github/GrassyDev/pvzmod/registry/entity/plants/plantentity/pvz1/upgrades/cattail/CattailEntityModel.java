package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.cattail;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CattailEntityModel extends AnimatedGeoModel<CattailEntity> {

    @Override
    public Identifier getModelResource(CattailEntity object)
    {
        return new Identifier("pvzmod", "geo/cattail.geo.json");
    }

	public Identifier getTextureResource(CattailEntity object) {
		return new Identifier("pvzmod", "textures/entity/cattail/cattail.png");
	}

    @Override
    public Identifier getAnimationResource(CattailEntity object)
    {
        return new Identifier ("pvzmod", "animations/cattail.json");
    }
}
