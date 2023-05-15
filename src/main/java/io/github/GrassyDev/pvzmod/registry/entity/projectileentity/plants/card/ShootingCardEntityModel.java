package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.card;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShootingCardEntityModel extends AnimatedGeoModel<ShootingCardEntity> {

    @Override
    public Identifier getModelResource(ShootingCardEntity object)
    {
        return new Identifier("pvzmod", "geo/card.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingCardEntity object){
		return new Identifier("pvzmod", "textures/entity/projectiles/card.png");
	}

    @Override
    public Identifier getAnimationResource(ShootingCardEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
