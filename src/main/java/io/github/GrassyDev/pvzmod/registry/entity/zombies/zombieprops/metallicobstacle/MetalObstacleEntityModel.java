package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallicobstacle;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MetalObstacleEntityModel extends AnimatedGeoModel<MetalObstacleEntity> {

    @Override
    public Identifier getModelResource(MetalObstacleEntity object)
    {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "geo/basketballbin.geo.json");
		if (object.getType().equals(PvZEntity.BASKETBALLBIN)) {
			identifier = new Identifier("pvzmod", "geo/basketballbin.geo.json");
		}
		else if (object.getType().equals(PvZEntity.TRASHCANBIN)) {
			identifier = new Identifier("pvzmod", "geo/trashcanbin.geo.json");
		}
		return identifier;
    }

    @Override
    public Identifier getTextureResource(MetalObstacleEntity object)
	{
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/obstacles/basketballbin.png");
		if (object.getType().equals(PvZEntity.BASKETBALLBIN)) {
			identifier = new Identifier("pvzmod", "textures/entity/obstacles/basketballbin.png");
			if (object.armless && object.geardmg){
				identifier = new Identifier("pvzmod", "textures/entity/obstacles/basketballbin_dmg1.png");
			}
			else if (object.armless && object.gearless){
				identifier = new Identifier("pvzmod", "textures/entity/obstacles/basketballbin_dmg1.png");
			}
			else if (object.gearless){
				identifier = new Identifier("pvzmod", "textures/entity/obstacles/basketballbin.png");
			}
			else if (object.geardmg){
				identifier = new Identifier("pvzmod", "textures/entity/obstacles/basketballbin.png");
			}
			else if (object.armless) {
				identifier = new Identifier("pvzmod", "textures/entity/obstacles/basketballbin_dmg1.png");
			}
		}
		else if (object.getType().equals(PvZEntity.TRASHCANBIN)) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/browncoat.png");
			if (object.armless && object.geardmg){
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/browncoat_dmg1.png");
			}
			else if (object.armless && object.gearless){
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/browncoat_dmg1.png");
			}
			else if (object.gearless){
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/browncoat.png");
			}
			else if (object.geardmg){
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/browncoat.png");
			}
			else if (object.armless) {
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/browncoat_dmg1.png");
			}
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(MetalObstacleEntity object)
    {
        return new Identifier ("pvzmod", "animations/gravestone.json");
    }
}
