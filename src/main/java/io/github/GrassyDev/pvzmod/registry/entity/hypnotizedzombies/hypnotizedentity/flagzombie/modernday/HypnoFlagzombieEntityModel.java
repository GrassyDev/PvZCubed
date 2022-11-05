package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.flagzombie.modernday;

import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.modernday.FlagzombieEntityRenderer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoFlagzombieEntityModel extends AnimatedGeoModel<HypnoFlagzombieEntity> {

    @Override
    public Identifier getModelResource(HypnoFlagzombieEntity object)
    {
        return new Identifier("pvzmod", "geo/flagzombie.geo.json");
    }

    @Override
    public Identifier getTextureResource(HypnoFlagzombieEntity object)
    {
		return HypnoFlagzombieEntityRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getAnimationResource(HypnoFlagzombieEntity object)
    {
        return new Identifier ("pvzmod", "animations/flagzombie.json");
    }
}
