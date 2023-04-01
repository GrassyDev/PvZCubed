package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.iceberg;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShootingIcebergEntityModel extends AnimatedGeoModel<ShootingIcebergEntity> {

    @Override
    public Identifier getModelResource(ShootingIcebergEntity object)
    {
        return new Identifier("pvzmod", "geo/iceberg.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingIcebergEntity object){
			return new Identifier("pvzmod", "textures/entity/cabbagepult/icebergpult.png");
	}

    @Override
    public Identifier getAnimationResource(ShootingIcebergEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
