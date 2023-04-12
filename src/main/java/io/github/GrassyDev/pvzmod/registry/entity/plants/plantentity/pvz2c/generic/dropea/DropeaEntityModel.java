package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.dropea;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DropeaEntityModel extends AnimatedGeoModel<DropeaEntity> {

    @Override
    public Identifier getModelResource(DropeaEntity object)
    {
        return new Identifier("pvzmod", "geo/dropea.geo.json");
    }

	public Identifier getTextureResource(DropeaEntity object) {
		return new Identifier(PvZCubed.MOD_ID, "textures/entity/peashooter/dropea.png");
	}

    @Override
    public Identifier getAnimationResource(DropeaEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashooter.json");
    }
}
