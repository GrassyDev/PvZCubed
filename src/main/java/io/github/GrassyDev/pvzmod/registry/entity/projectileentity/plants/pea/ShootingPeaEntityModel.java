package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShootingPeaEntityModel extends AnimatedGeoModel<ShootingPeaEntity> {

    @Override
    public Identifier getModelResource(ShootingPeaEntity object)
    {
        return new Identifier("pvzmod", "geo/peashot.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingPeaEntity object){
		return ShootingPeaEntityRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
	}

    @Override
    public Identifier getAnimationResource(ShootingPeaEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
