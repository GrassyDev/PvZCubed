package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.hypnoproj;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoProjModel extends AnimatedGeoModel<HypnoProjEntity> {

    @Override
    public Identifier getModelResource(HypnoProjEntity object)
    {
        return new Identifier("pvzmod", "geo/hypnoproj.geo.json");
    }

    @Override
    public Identifier getTextureResource(HypnoProjEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/hypnoproj.png");
    }

    @Override
    public Identifier getAnimationResource(HypnoProjEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
