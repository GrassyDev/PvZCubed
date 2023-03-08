package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pepper;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShootingPepperEntityModel extends AnimatedGeoModel<ShootingPepperEntity> {

    @Override
    public Identifier getModelResource(ShootingPepperEntity object)
    {
        return new Identifier("pvzmod", "geo/pepperproj.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingPepperEntity object){
			return new Identifier("pvzmod", "textures/entity/projectiles/pepperproj.png");
	}

    @Override
    public Identifier getAnimationResource(ShootingPepperEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
