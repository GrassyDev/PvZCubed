package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.jingle;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class JingleEntityModel extends AnimatedGeoModel<JingleEntity> {

    @Override
    public Identifier getModelResource(JingleEntity object)
    {
        return new Identifier("pvzmod", "geo/blank.geo.json");
    }

    @Override
    public Identifier getTextureResource(JingleEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/peashot.png");
    }

    @Override
    public Identifier getAnimationResource(JingleEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
