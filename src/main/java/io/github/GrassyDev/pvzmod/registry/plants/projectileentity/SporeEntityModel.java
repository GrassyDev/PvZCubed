package io.github.GrassyDev.pvzmod.registry.plants.projectileentity;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SporeEntityModel extends AnimatedGeoModel<SporeEntity> {

    @Override
    public Identifier getModelResource(SporeEntity object)
    {
        return new Identifier("pvzmod", "geo/peashot.geo.json");
    }

    @Override
    public Identifier getTextureResource(SporeEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/fume.png");
    }

    @Override
    public Identifier getAnimationResource(SporeEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
