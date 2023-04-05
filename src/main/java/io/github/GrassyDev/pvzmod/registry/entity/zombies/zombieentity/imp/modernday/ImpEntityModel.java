package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.modernday;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.ExplorerVariants;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.ImpVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.explorer.ExplorerEntityRenderer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ImpEntityModel extends AnimatedGeoModel<ImpEntity> {

    @Override
    public Identifier getModelResource(ImpEntity object)
    {
		return ImpEntityRenderer.LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getTextureResource(ImpEntity object)
    {
		Identifier identifier;
		if (object.getVariant().equals(ImpVariants.IMPDRAGON) || object.getVariant().equals(ImpVariants.IMPDRAGONHYPNO)) {
			identifier = new Identifier("pvzmod", "textures/entity/imp/impdragon.png");
			if (object.getType().equals(PvZEntity.IMPHYPNO)) {
				identifier = new Identifier("pvzmod", "textures/entity/imp/impdragon_hypnotized.png");
			}
		}
		else {
			identifier = new Identifier("pvzmod", "textures/entity/imp/imp.png");
			if (object.getType().equals(PvZEntity.IMPHYPNO)) {
				identifier = new Identifier("pvzmod", "textures/entity/imp/imp_hypnotized.png");
			}
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(ImpEntity object)
    {
        return new Identifier ("pvzmod", "animations/imp.json");
    }
}
