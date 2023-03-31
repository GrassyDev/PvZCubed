package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombiemachines.robocone;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RoboConeEntityModel extends AnimatedGeoModel<RoboConeEntity> {

    @Override
    public Identifier getModelResource(RoboConeEntity object)
    {
		return new Identifier("pvzmod", "geo/robocone.geo.json");
    }

    @Override
    public Identifier getTextureResource(RoboConeEntity object) {
		Identifier identifier = new Identifier("pvzmod", "textures/entity/robocone/robocone.png");;
		if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/robocone/robocone_dmg1.png");
		}
		return identifier;
	}

    @Override
    public Identifier getAnimationResource(RoboConeEntity object)
    {
        return new Identifier ("pvzmod", "animations/robocone.json");
    }
}
