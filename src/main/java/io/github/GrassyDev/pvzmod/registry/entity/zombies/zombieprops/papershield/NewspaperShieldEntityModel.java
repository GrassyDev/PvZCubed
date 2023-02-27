package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.papershield;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NewspaperShieldEntityModel extends AnimatedGeoModel<NewspaperShieldEntity> {

    @Override
    public Identifier getModelResource(NewspaperShieldEntity object)
    {
        return new Identifier("pvzmod", "geo/blank.geo.json");
    }

    @Override
    public Identifier getTextureResource(NewspaperShieldEntity object)
    {
		return new Identifier("pvzmod", "textures/entity/projectiles/peashot.png");
    }

    @Override
    public Identifier getAnimationResource(NewspaperShieldEntity object)
    {
		return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
