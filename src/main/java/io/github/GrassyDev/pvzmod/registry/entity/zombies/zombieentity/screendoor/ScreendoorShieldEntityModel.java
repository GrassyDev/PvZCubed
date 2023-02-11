package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.screendoor;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ScreendoorShieldEntityModel extends AnimatedGeoModel<ScreendoorShieldEntity> {

    @Override
    public Identifier getModelResource(ScreendoorShieldEntity object)
    {
        return new Identifier("pvzmod", "geo/blank.geo.json");
    }

    @Override
    public Identifier getTextureResource(ScreendoorShieldEntity object)
    {
		return new Identifier("pvzmod", "textures/entity/projectiles/peashot.png");
    }

    @Override
    public Identifier getAnimationResource(ScreendoorShieldEntity object)
    {
		return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
