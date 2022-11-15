package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.squash;

import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.chomper.ChomperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.chomper.ChomperEntityRenderer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SquashEntityModel extends AnimatedGeoModel<SquashEntity> {

    @Override
    public Identifier getModelResource(SquashEntity object)
    {
        return new Identifier("pvzmod", "geo/squash.geo.json");
    }

	public Identifier getTextureResource(SquashEntity object)
	{
		return new Identifier("pvzmod", "textures/entity/squash/squash.png");
	}

    @Override
    public Identifier getAnimationResource(SquashEntity object)
    {
        return new Identifier ("pvzmod", "animations/squash.json");
    }
}
