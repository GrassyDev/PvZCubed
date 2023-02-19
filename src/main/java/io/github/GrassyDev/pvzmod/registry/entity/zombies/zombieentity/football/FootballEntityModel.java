package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.football;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class FootballEntityModel extends AnimatedGeoModel<FootballEntity> {

    @Override
	public Identifier getModelResource(FootballEntity object)
	{
		return FootballEntityRenderer.LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
	}

	@Override
	public Identifier getTextureResource(FootballEntity object)
	{
		return FootballEntityRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
	}

    @Override
    public Identifier getAnimationResource(FootballEntity object)
    {
        return new Identifier ("pvzmod", "animations/football.json");
    }
}
