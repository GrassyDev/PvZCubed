package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pumpkinzombie;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PumpkinZombieEntityModel extends AnimatedGeoModel<PumpkinZombieEntity> {

    @Override
    public Identifier getModelResource(PumpkinZombieEntity object)
    {
		return new Identifier("pvzmod", "geo/pumpkinzombie.geo.json");
    }

    @Override
    public Identifier getTextureResource(PumpkinZombieEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/browncoat/pumpkin/pumpkinzombie.png");
		if (object.armless && object.geardmg) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/pumpkin/pumpkinzombie_dmg1_geardmg1.png");
		} else if (object.armless && object.gearless) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/pumpkin/pumpkinzombie_gearless_dmg1.png");
		} else if (object.gearless) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/pumpkin/pumpkinzombie_gearless.png");
		} else if (object.geardmg) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/pumpkin/pumpkinzombie_geardmg1.png");
		} else if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/pumpkin/pumpkinzombie_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(PumpkinZombieEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
