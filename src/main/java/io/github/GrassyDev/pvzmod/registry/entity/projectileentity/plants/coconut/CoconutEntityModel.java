package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.coconut;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CoconutEntityModel extends AnimatedGeoModel<CoconutEntity> {

    @Override
    public Identifier getModelResource(CoconutEntity object)
    {
        return new Identifier("pvzmod", "geo/bigpea.geo.json");
    }

    @Override
    public Identifier getTextureResource(CoconutEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/coconut.png");
    }

    @Override
    public Identifier getAnimationResource(CoconutEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
