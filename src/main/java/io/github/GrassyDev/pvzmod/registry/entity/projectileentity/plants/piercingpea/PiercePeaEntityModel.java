package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.piercingpea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PiercePeaEntityModel extends AnimatedGeoModel<PiercePeaEntity> {

    @Override
    public Identifier getModelResource(PiercePeaEntity object)
    {
        return new Identifier("pvzmod", "geo/spit.geo.json");
    }

    @Override
    public Identifier getTextureResource(PiercePeaEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/peashot.png");
    }

    @Override
    public Identifier getAnimationResource(PiercePeaEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
