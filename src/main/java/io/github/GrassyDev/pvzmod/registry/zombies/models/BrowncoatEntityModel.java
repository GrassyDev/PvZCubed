package io.github.GrassyDev.pvzmod.registry.zombies.models;

import io.github.GrassyDev.pvzmod.registry.zombies.zombieentity.BrowncoatEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BrowncoatEntityModel extends AnimatedGeoModel<BrowncoatEntity> {

    @Override
    public Identifier getModelResource(BrowncoatEntity object)
    {
        return new Identifier("pvzcubed", "geo/browncoat.geo.json");
    }

    @Override
    public Identifier getTextureResource(BrowncoatEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/browncoat/browncoat.png");
    }

    @Override
    public Identifier getAnimationResource(BrowncoatEntity object)
    {
        return new Identifier ("pvzcubed", "animations/newbrowncoat.json");
    }
}
