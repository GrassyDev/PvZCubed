package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SnorkelEntityModel extends AnimatedGeoModel<SnorkelEntity> {

    @Override
    public Identifier getModelResource(SnorkelEntity object)
    {
        return new Identifier("pvzmod", "geo/snorkel.geo.json");
    }

    @Override
    public Identifier getTextureResource(SnorkelEntity object)
    {
		Identifier identifier;
		if (object.getHypno()){
			identifier = new Identifier("pvzmod", "textures/entity/snorkel/snorkel_hypnotized.png");
			if (object.armless){
				identifier = new Identifier("pvzmod", "textures/entity/snorkel/snorkel_dmg1_hypnotized.png");
			}
		}
		else {
			identifier = new Identifier("pvzmod", "textures/entity/snorkel/snorkel.png");
			if (object.armless){
				identifier = new Identifier("pvzmod", "textures/entity/snorkel/snorkel_dmg1.png");
			}
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(SnorkelEntity object)
    {
        return new Identifier ("pvzmod", "animations/snorkel.json");
    }
}
