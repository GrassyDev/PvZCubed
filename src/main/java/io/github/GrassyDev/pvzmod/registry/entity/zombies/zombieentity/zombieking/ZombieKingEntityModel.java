package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.zombieking;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ZombieKingEntityModel extends AnimatedGeoModel<ZombieKingEntity> {

    @Override
    public Identifier getModelResource(ZombieKingEntity object)
    {
        return new Identifier("pvzmod", "geo/zombieking.geo.json");
    }

    @Override
    public Identifier getTextureResource(ZombieKingEntity object)
    {
		Identifier identifier = new Identifier("pvzmod", "textures/entity/zombieking/zombieking.png");
		if (object.getHypno()){
			identifier = new Identifier("pvzmod", "textures/entity/zombieking/zombieking_hypnotized.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(ZombieKingEntity object)
    {
        return new Identifier ("pvzmod", "animations/zombieking.json");
    }
}
