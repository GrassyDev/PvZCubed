package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.screendoor;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ScreendoorEntityModel extends AnimatedGeoModel<ScreendoorEntity> {

    @Override
    public Identifier getModelResource(ScreendoorEntity object)
    {
        return new Identifier("pvzmod", "geo/screendoor.geo.json");
    }

    @Override
    public Identifier getTextureResource(ScreendoorEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/browncoat/screendoor.png");
    }

    @Override
    public Identifier getAnimationResource(ScreendoorEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
