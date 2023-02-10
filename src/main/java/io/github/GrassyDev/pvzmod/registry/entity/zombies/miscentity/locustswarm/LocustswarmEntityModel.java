package io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.locustswarm;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LocustswarmEntityModel extends AnimatedGeoModel<LocustSwarmEntity> {

    @Override
    public Identifier getModelResource(LocustSwarmEntity object) {
		return new Identifier("pvzmod", "geo/blank.geo.json");
	}

	@Override
	public Identifier getTextureResource(LocustSwarmEntity object)
	{
		return new Identifier("pvzmod", "textures/entity/projectiles/peashot.png");
	}

	@Override
	public Identifier getAnimationResource(LocustSwarmEntity object)
	{
		return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
