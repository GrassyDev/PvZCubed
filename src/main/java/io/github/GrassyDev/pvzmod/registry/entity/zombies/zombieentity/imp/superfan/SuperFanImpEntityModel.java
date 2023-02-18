package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.superfan;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SuperFanImpEntityModel extends AnimatedGeoModel<SuperFanImpEntity> {

    @Override
    public Identifier getModelResource(SuperFanImpEntity object)
    {
        return new Identifier("pvzmod", "geo/superfanimp.geo.json");
    }

    @Override
    public Identifier getTextureResource(SuperFanImpEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/imp/superfanimp.png");
    }

    @Override
    public Identifier getAnimationResource(SuperFanImpEntity object)
    {
        return new Identifier ("pvzmod", "animations/imp.json");
    }
}
