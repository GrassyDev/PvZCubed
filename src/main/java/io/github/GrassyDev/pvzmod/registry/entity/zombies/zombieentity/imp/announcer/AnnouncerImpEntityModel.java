package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.announcer;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
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
		Identifier identifier = new Identifier("pvzmod", "textures/entity/imp/announcerimp.png");
		if (object.getType().equals(PvZEntity.IMPHYPNO)){
			identifier = new Identifier("pvzmod", "textures/entity/imp/announcerimp_hypnotized.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(AnnouncerImpEntity object)
    {
        return new Identifier ("pvzmod", "animations/imp.json");
    }
}
