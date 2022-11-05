package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.conehead.modernday;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class ConeheadEntityModel extends AnimatedGeoModel<ConeheadEntity> {

    @Override
    public Identifier getModelResource(ConeheadEntity object)
    {
        return new Identifier("pvzmod", "geo/conehead.geo.json");
    }

    @Override
    public Identifier getTextureResource(ConeheadEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/browncoat/conehead.png");
    }

    @Override
    public Identifier getAnimationResource(ConeheadEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
