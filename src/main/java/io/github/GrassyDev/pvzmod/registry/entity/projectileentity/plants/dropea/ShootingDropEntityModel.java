package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.dropea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShootingDropEntityModel extends AnimatedGeoModel<ShootingDropEntity> {

    @Override
    public Identifier getModelResource(ShootingDropEntity object)
    {
        return new Identifier("pvzmod", "geo/peashot.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingDropEntity object){
		return new Identifier("pvzmod", "textures/entity/projectiles/drop.png");
	}

    @Override
    public Identifier getAnimationResource(ShootingDropEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
