package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.modernday;

import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.fumeshroom.FumeshroomEntityRenderer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FlagzombieEntityModel extends AnimatedGeoModel<FlagzombieEntity> {

    @Override
    public Identifier getModelResource(FlagzombieEntity object)
    {
        return new Identifier("pvzmod", "geo/flagzombie.geo.json");
    }

    @Override
    public Identifier getTextureResource(FlagzombieEntity object)
    {
		return FlagzombieEntityRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getAnimationResource(FlagzombieEntity object)
    {
        return new Identifier ("pvzmod", "animations/flagzombie.json");
    }
}
