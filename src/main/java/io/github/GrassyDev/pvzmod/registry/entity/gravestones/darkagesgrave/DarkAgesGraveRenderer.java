package io.github.GrassyDev.pvzmod.registry.entity.gravestones.darkagesgrave;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class DarkAgesGraveRenderer extends GeoEntityRenderer<DarkAgesGraveEntity> {

    public DarkAgesGraveRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new DarkAgesGraveModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

}
