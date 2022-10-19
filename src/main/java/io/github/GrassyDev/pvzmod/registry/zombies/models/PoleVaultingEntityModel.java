package io.github.GrassyDev.pvzmod.registry.zombies.models;

import io.github.GrassyDev.pvzmod.registry.zombies.zombieentity.PoleVaultingEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PoleVaultingEntityModel extends AnimatedGeoModel<PoleVaultingEntity> {

    @Override
    public Identifier getModelResource(PoleVaultingEntity object)
    {
        return new Identifier("pvzcubed", "geo/polevaulting.geo.json");
    }

    @Override
    public Identifier getTextureResource(PoleVaultingEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/browncoat/polevaulting.png");
    }

    @Override
    public Identifier getAnimationResource(PoleVaultingEntity object)
    {
        return new Identifier ("pvzcubed", "animations/polevaulting.json");
    }
}
