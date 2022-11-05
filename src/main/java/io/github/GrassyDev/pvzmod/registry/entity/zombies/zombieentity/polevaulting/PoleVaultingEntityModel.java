package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.polevaulting;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PoleVaultingEntityModel extends AnimatedGeoModel<PoleVaultingEntity> {

    @Override
    public Identifier getModelResource(PoleVaultingEntity object)
    {
        return new Identifier("pvzmod", "geo/polevaulting.geo.json");
    }

    @Override
    public Identifier getTextureResource(PoleVaultingEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/browncoat/polevaulting.png");
    }

    @Override
    public Identifier getAnimationResource(PoleVaultingEntity object)
    {
        return new Identifier ("pvzmod", "animations/polevaulting.json");
    }
}
