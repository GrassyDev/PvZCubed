package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.backupdancer;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BackupDancerEntityModel extends AnimatedGeoModel<BackupDancerEntity> {

    @Override
    public Identifier getModelResource(BackupDancerEntity object)
    {
        return new Identifier("pvzmod", "geo/backupdancer.geo.json");
    }

    @Override
    public Identifier getTextureResource(BackupDancerEntity object)
    {
		Identifier identifier;
		if (object.getHypno()){
			identifier = new Identifier("pvzmod", "textures/entity/dancingzombie/backupdancer_hypnotized.png");
			if (object.armless && object.geardmg){
				identifier = new Identifier("pvzmod", "textures/entity/dancingzombie/backupdancer_dmg1_geardmg1_hypnotized.png");
			}
			else if (object.armless && object.gearless){
				identifier = new Identifier("pvzmod", "textures/entity/dancingzombie/backupdancer_gearless_dmg1_hypnotized.png");
			}
			else if (object.gearless){
				identifier = new Identifier("pvzmod", "textures/entity/dancingzombie/backupdancer_gearless_hypnotized.png");
			}
			else if (object.geardmg){
				identifier = new Identifier("pvzmod", "textures/entity/dancingzombie/backupdancer_geardmg1_hypnotized.png");
			}
			else if (object.armless){
				identifier = new Identifier("pvzmod", "textures/entity/dancingzombie/backupdancer_dmg1_hypnotized.png");
			}
		}
		else {
			identifier = new Identifier("pvzmod", "textures/entity/dancingzombie/backupdancer.png");
			if (object.armless && object.geardmg){
				identifier = new Identifier("pvzmod", "textures/entity/dancingzombie/backupdancer_dmg1_geardmg1.png");
			}
			else if (object.armless && object.gearless){
				identifier = new Identifier("pvzmod", "textures/entity/dancingzombie/backupdancer_gearless_dmg1.png");
			}
			else if (object.gearless){
				identifier = new Identifier("pvzmod", "textures/entity/dancingzombie/backupdancer_gearless.png");
			}
			else if (object.geardmg){
				identifier = new Identifier("pvzmod", "textures/entity/dancingzombie/backupdancer_geardmg1.png");
			}
			else if (object.armless){
				identifier = new Identifier("pvzmod", "textures/entity/dancingzombie/backupdancer_dmg1.png");
			}
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(BackupDancerEntity object)
    {
        return new Identifier ("pvzmod", "animations/backupdancer.json");
    }
}
