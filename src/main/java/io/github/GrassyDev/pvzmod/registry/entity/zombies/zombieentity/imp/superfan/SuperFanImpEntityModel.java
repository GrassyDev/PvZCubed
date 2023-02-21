package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.superfan;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
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
    public Identifier getTextureResource(SuperFanImpEntity object) {
		Identifier identifier;
		if (object.getType().equals(PvZEntity.SUPERFANIMPHYPNO)) {
			identifier = new Identifier("pvzmod", "textures/entity/imp/superfanimp_hypnotized.png");
		} else if (object.getType().equals(PvZEntity.NEWYEARIMP)) {
			identifier = new Identifier("pvzmod", "textures/entity/imp/newyearimp.png");
		} else if (object.getType().equals(PvZEntity.NEWYEARIMPHYPNO)) {
			identifier = new Identifier("pvzmod", "textures/entity/imp/newyearimp_hypnotized.png");
		}
		else{
			identifier = new Identifier("pvzmod", "textures/entity/imp/superfanimp.png");
		}
		return identifier;
	}

    @Override
    public Identifier getAnimationResource(SuperFanImpEntity object)
    {
        return new Identifier ("pvzmod", "animations/imp.json");
    }
}
