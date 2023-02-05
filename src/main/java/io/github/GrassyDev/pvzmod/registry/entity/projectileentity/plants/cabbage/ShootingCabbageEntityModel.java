package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.cabbage;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShootingCabbageEntityModel extends AnimatedGeoModel<ShootingCabbageEntity> {

    @Override
    public Identifier getModelResource(ShootingCabbageEntity object)
    {
        return new Identifier("pvzmod", "geo/cabbage.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingCabbageEntity object){
			return new Identifier("pvzmod", "textures/entity/projectiles/cabbage.png");
	}

    @Override
    public Identifier getAnimationResource(ShootingCabbageEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
