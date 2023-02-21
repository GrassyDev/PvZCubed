package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class GargantuarEntityModel extends AnimatedGeoModel<GargantuarEntity> {

    @Override
    public Identifier getModelResource(GargantuarEntity object)
    {
        return new Identifier("pvzmod", "geo/gargantuar.geo.json");
    }

    @Override
    public Identifier getTextureResource(GargantuarEntity object)
    {
		Identifier identifier = new Identifier("pvzmod", "textures/entity/gargantuar/gargantuar.png");
		if (object.getType().equals(PvZEntity.GARGANTUARHYPNO)){
			identifier = new Identifier("pvzmod", "textures/entity/gargantuar/gargantuar_hypnotized.png");
		}
        return identifier;
    }

    @Override
    public Identifier getAnimationResource(GargantuarEntity object)
    {
        return new Identifier ("pvzmod", "animations/gargantuar.json");
    }
}
