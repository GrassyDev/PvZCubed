package net.fabricmc.example.registry.zombies.models;

import net.fabricmc.example.registry.zombies.zombieentity.BrowncoatEntity;
import net.fabricmc.example.registry.zombies.zombieentity.PoleVaultingEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PoleVaultingEntityModel extends AnimatedGeoModel<PoleVaultingEntity> {

    @Override
    public Identifier getModelLocation(PoleVaultingEntity object)
    {
        return new Identifier("pvzcubed", "geo/polevaulting.geo.json");
    }

    @Override
    public Identifier getTextureLocation(PoleVaultingEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/browncoat/polevaulting.png");
    }

    @Override
    public Identifier getAnimationFileLocation(PoleVaultingEntity object)
    {
        return new Identifier ("pvzcubed", "animations/polevaulting.json");
    }
}