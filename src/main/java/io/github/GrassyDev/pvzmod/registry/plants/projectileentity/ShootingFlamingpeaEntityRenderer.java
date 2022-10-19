package io.github.GrassyDev.pvzmod.registry.plants.projectileentity;

import io.github.GrassyDev.pvzmod.registry.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ShootingFlamingpeaEntityRenderer extends EntityRenderer<ShootingFlamingPeaEntity> {

    public static ItemStack STACK = new ItemStack(ModItems.REPEA);

    public ShootingFlamingpeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
	}

    public void render(ShootingFlamingPeaEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int seed) {

		matrices.push();

		MinecraftClient.getInstance().getItemRenderer().renderItem(
				STACK,
				ModelTransformation.Mode.FIXED,
				light,
				OverlayTexture.DEFAULT_UV,
				matrices,
				vertexConsumers,
				seed
		);

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);

    }

    @Override
    public Identifier getTexture(ShootingFlamingPeaEntity entity) {
        return null;
    }
}
