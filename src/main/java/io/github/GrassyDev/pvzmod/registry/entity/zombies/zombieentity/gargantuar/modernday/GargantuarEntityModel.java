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
		return GargantuarEntityRenderer.LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getTextureResource(GargantuarEntity object)
    {
		Identifier identifier = new Identifier("pvzmod", "textures/entity/gargantuar/gargantuar.png");
		if (object.getType().equals(PvZEntity.GARGANTUARHYPNO)){
			identifier = new Identifier("pvzmod", "textures/entity/gargantuar/gargantuar_hypnotized.png");
		}
		else if (object.getType().equals(PvZEntity.DEFENSIVEEND)){
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
        return identifier;
    }

    @Override
    public Identifier getAnimationResource(GargantuarEntity object)
    {
        return new Identifier ("pvzmod", "animations/gargantuar.json");
    }
}
