package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.announcer;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AnnouncerImpEntityModel extends AnimatedGeoModel<AnnouncerImpEntity> {

    @Override
    public Identifier getModelResource(AnnouncerImpEntity object)
    {
        return new Identifier("pvzmod", "geo/announcerimp.geo.json");
    }

    @Override
    public Identifier getTextureResource(AnnouncerImpEntity object)
    {
		return new Identifier("pvzmod", "textures/entity/imp/announcerimp.png");
    }

    @Override
    public Identifier getAnimationResource(AnnouncerImpEntity object)
    {
        return new Identifier ("pvzmod", "animations/imp.json");
    }
}
