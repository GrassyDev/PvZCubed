package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.dancingzombie;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DancingZombieEntityModel extends AnimatedGeoModel<DancingZombieEntity> {

    @Override
    public Identifier getModelResource(DancingZombieEntity object)
    {
        return new Identifier("pvzmod", "geo/dancingzombie.geo.json");
    }

    @Override
    public Identifier getTextureResource(DancingZombieEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/dancingzombie/dancingzombie.png");
		if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/dancingzombie/dancingzombie_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(DancingZombieEntity object)
    {
        return new Identifier ("pvzmod", "animations/dancingzombie.json");
    }
}
