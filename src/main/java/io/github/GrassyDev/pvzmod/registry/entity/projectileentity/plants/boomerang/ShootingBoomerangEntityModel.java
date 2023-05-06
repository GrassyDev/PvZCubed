package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.boomerang;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShootingBoomerangEntityModel extends AnimatedGeoModel<ShootingBoomerangEntity> {

    @Override
    public Identifier getModelResource(ShootingBoomerangEntity object)
    {
        return new Identifier("pvzmod", "geo/boomerang.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingBoomerangEntity object){
		return new Identifier("pvzmod", "textures/entity/bloomerang/bloomerang.png");
	}

    @Override
    public Identifier getAnimationResource(ShootingBoomerangEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
