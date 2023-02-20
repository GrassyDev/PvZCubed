package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.football;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class FootballEntityModel extends AnimatedGeoModel<FootballEntity> {

    @Override
	public Identifier getModelResource(FootballEntity object)
	{
		return FootballEntityRenderer.LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
	}

	@Override
	public Identifier getTextureResource(FootballEntity object)
	{
		Identifier identifier;
		if (object.getType().equals(PvZEntity.FOOTBALLHYPNO)){
			identifier = new Identifier("pvzmod", "textures/entity/football/football_hypnotized.png");
			if (object.armless && object.geardmg){
				identifier = new Identifier("pvzmod", "textures/entity/football/football_dmg1_geardmg1_hypnotized.png");
			}
			else if (object.armless && object.gearless){
				identifier = new Identifier("pvzmod", "textures/entity/football/football_gearless_dmg1_hypnotized.png");
			}
			else if (object.gearless){
				identifier = new Identifier("pvzmod", "textures/entity/football/football_gearless_hypnotized.png");
			}
			else if (object.geardmg){
				identifier = new Identifier("pvzmod", "textures/entity/football/football_geardmg1_hypnotized.png");
			}
			else if (object.armless){
				identifier = new Identifier("pvzmod", "textures/entity/football/football_dmg1_hypnotized.png");
			}
		}
		else if (object.getType().equals(PvZEntity.BERSERKERHYPNO)){
			identifier = new Identifier("pvzmod", "textures/entity/football/berserker_hypnotized.png");
			if (object.armless && object.geardmg){
				identifier = new Identifier("pvzmod", "textures/entity/football/berserker_dmg1_geardmg1_hypnotized.png");
			}
			else if (object.armless && object.gearless){
				identifier = new Identifier("pvzmod", "textures/entity/football/berserker_gearless_dmg1_hypnotized.png");
			}
			else if (object.gearless){
				identifier = new Identifier("pvzmod", "textures/entity/football/berserker_gearless_hypnotized.png");
			}
			else if (object.geardmg){
				identifier = new Identifier("pvzmod", "textures/entity/football/berserker_geardmg1_hypnotized.png");
			}
			else if (object.armless){
				identifier = new Identifier("pvzmod", "textures/entity/football/berserker_dmg1_hypnotized.png");
			}
		}
		else if (object.getType().equals(PvZEntity.BERSERKER)){
			identifier = new Identifier("pvzmod", "textures/entity/football/berserker.png");
			if (object.armless && object.geardmg){
				identifier = new Identifier("pvzmod", "textures/entity/football/berserker_dmg1_geardmg1.png");
			}
			else if (object.armless && object.gearless){
				identifier = new Identifier("pvzmod", "textures/entity/football/berserker_gearless_dmg1.png");
			}
			else if (object.gearless){
				identifier = new Identifier("pvzmod", "textures/entity/football/berserker_gearless.png");
			}
			else if (object.geardmg){
				identifier = new Identifier("pvzmod", "textures/entity/football/berserker_geardmg1.png");
			}
			else if (object.armless){
				identifier = new Identifier("pvzmod", "textures/entity/football/berserker_dmg1.png");
			}
		}
		else {
			identifier = new Identifier("pvzmod", "textures/entity/football/football.png");
			if (object.armless && object.geardmg){
				identifier = new Identifier("pvzmod", "textures/entity/football/football_dmg1_geardmg1.png");
			}
			else if (object.armless && object.gearless){
				identifier = new Identifier("pvzmod", "textures/entity/football/football_gearless_dmg1.png");
			}
			else if (object.gearless){
				identifier = new Identifier("pvzmod", "textures/entity/football/football_gearless.png");
			}
			else if (object.geardmg){
				identifier = new Identifier("pvzmod", "textures/entity/football/football_geardmg1.png");
			}
			else if (object.armless){
				identifier = new Identifier("pvzmod", "textures/entity/football/football_dmg1.png");
			}
		}
		return identifier;
	}

    @Override
    public Identifier getAnimationResource(FootballEntity object)
    {
        return new Identifier ("pvzmod", "animations/football.json");
    }
}
