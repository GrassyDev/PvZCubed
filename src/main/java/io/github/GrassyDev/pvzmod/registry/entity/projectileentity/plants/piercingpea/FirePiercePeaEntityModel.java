package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.piercingpea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FirePiercePeaEntityModel extends AnimatedGeoModel<FirePiercePeaEntity> {

    @Override
    public Identifier getModelResource(FirePiercePeaEntity object)
    {
        return new Identifier("pvzmod", "geo/spit.geo.json");
    }

    @Override
    public Identifier getTextureResource(FirePiercePeaEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/firepea.png");
    }

    @Override
    public Identifier getAnimationResource(FirePiercePeaEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
