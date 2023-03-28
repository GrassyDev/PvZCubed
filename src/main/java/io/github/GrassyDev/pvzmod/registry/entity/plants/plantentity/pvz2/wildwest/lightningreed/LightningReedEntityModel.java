package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.wildwest.lightningreed;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LightningReedEntityModel extends AnimatedGeoModel<LightningReedEntity> {

    @Override
    public Identifier getModelResource(LightningReedEntity object)
    {
        return new Identifier("pvzmod", "geo/lightningreed.geo.json");
    }

	public Identifier getTextureResource(LightningReedEntity object) {
		return new Identifier("pvzmod", "textures/entity/lightningreed/lightningreed.png");
	}

    @Override
    public Identifier getAnimationResource(LightningReedEntity object)
    {
        return new Identifier ("pvzmod", "animations/lightningreed.json");
    }
}
