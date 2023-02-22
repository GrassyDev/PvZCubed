package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.newspaper;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NewspaperEntityModel extends AnimatedGeoModel<NewspaperEntity> {

    @Override
    public Identifier getModelResource(NewspaperEntity object)
    {
        return new Identifier("pvzmod", "geo/newspaper.geo.json");
    }

    @Override
    public Identifier getTextureResource(NewspaperEntity object)
    {
		Identifier identifier;
		if (object.getHypno()){
			identifier = new Identifier("pvzmod", "textures/entity/newspaper/newspaper_hypnotized.png");
			if (object.armless){
				identifier = new Identifier("pvzmod", "textures/entity/newspaper/newspaper_dmg1_hypnotized.png");
			}
		}
		else {
			identifier = new Identifier("pvzmod", "textures/entity/newspaper/newspaper.png");
			if (object.armless){
				identifier = new Identifier("pvzmod", "textures/entity/newspaper/newspaper_dmg1.png");
			}
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(NewspaperEntity object)
    {
        return new Identifier ("pvzmod", "animations/newspaper.json");
    }
}
