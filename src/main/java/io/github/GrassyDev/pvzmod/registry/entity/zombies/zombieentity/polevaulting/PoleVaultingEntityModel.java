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
    public Identifier getTextureResource(PoleVaultingEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/polevaulting/polevaulting.png");
		if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/polevaulting/polevaulting_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(PoleVaultingEntity object)
    {
        return new Identifier ("pvzmod", "animations/polevaulting.json");
    }
}
