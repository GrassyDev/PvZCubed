package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.defensiveend;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class DefensiveEndEntityModel extends AnimatedGeoModel<DefensiveEndEntity> {

    @Override
    public Identifier getModelResource(DefensiveEndEntity object)
    {
        return new Identifier("pvzmod", "geo/defensiveend.geo.json");
    }

    @Override
    public Identifier getTextureResource(DefensiveEndEntity object)
    {
		Identifier identifier;
		if (object.getType().equals(PvZEntity.DEFENSIVEEND)){
			identifier = new Identifier("pvzmod", "textures/entity/gargantuar/defensiveend.png");
			if (object.gearless){
				identifier = new Identifier("pvzmod", "textures/entity/gargantuar/defensiveend_gearless.png");
			}
		}
		else if (object.getType().equals(PvZEntity.DEFENSIVEENDHYPNO)){
			identifier = new Identifier("pvzmod", "textures/entity/gargantuar/defensiveend_hypnotized.png");
			if (object.gearless){
				identifier = new Identifier("pvzmod", "textures/entity/gargantuar/defensiveend_gearless_hypnotized.png");
			}
		}
		else if (object.getType().equals(PvZEntity.DEFENSIVEEND_NEWYEAR)){
			identifier = new Identifier("pvzmod", "textures/entity/gargantuar/defensiveend_newyear.png");
			if (object.gearless){
				identifier = new Identifier("pvzmod", "textures/entity/gargantuar/defensiveend_newyear_gearless.png");
			}
		}
		else if (object.getType().equals(PvZEntity.DEFENSIVEEND_NEWYEARHYPNO)){
			identifier = new Identifier("pvzmod", "textures/entity/gargantuar/defensiveend_newyear_hypnotized.png");
			if (object.gearless){
				identifier = new Identifier("pvzmod", "textures/entity/gargantuar/defensiveend_newyear_gearless_hypnotized.png");
			}
		}
		else {
			identifier = new Identifier("pvzmod", "textures/entity/gargantuar/defensiveend.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(DefensiveEndEntity object)
    {
        return new Identifier ("pvzmod", "animations/gargantuar.json");
    }
}
