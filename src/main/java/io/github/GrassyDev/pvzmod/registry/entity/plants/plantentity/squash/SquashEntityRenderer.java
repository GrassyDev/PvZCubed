package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.squash;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.chomper.ChomperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.chomper.ChomperEntityModel;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.ChomperVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SquashEntityRenderer extends GeoEntityRenderer<SquashEntity> {

    public SquashEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SquashEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
