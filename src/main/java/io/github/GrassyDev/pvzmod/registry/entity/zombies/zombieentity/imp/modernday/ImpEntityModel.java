package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.modernday;

import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.BrowncoatEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ImpEntityModel extends AnimatedGeoModel<ImpEntity> {

    @Override
    public Identifier getModelResource(ImpEntity object)
    {
        return new Identifier("pvzmod", "geo/imp.geo.json");
    }

    @Override
    public Identifier getTextureResource(ImpEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/imp/imp.png");
    }

    @Override
    public Identifier getAnimationResource(ImpEntity object)
    {
        return new Identifier ("pvzmod", "animations/imp.json");
    }
}
