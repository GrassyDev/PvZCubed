package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.skycity.saucer;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SaucerEntityModel extends AnimatedGeoModel<SaucerEntity> {

    @Override
    public Identifier getModelResource(SaucerEntity object)
    {
        return new Identifier("pvzmod", "geo/saucer.geo.json");
    }

	public Identifier getTextureResource(SaucerEntity object) {
		return new Identifier("pvzmod", "textures/entity/loquat/saucer.png");
	}

    @Override
    public Identifier getAnimationResource(SaucerEntity object)
    {
        return new Identifier ("pvzmod", "animations/loquat.json");
    }
}
