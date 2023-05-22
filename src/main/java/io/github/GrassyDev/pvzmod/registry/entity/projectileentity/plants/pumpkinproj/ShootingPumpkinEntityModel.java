package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pumpkinproj;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShootingPumpkinEntityModel extends AnimatedGeoModel<ShootingPumpkinEntity> {

    @Override
    public Identifier getModelResource(ShootingPumpkinEntity object)
    {
        return new Identifier("pvzmod", "geo/pumpkinproj.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingPumpkinEntity object){
			return new Identifier("pvzmod", "textures/entity/pumpkinwitch/pumpkinwitch.png");
	}

    @Override
    public Identifier getAnimationResource(ShootingPumpkinEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
