package io.github.GrassyDev.pvzmod.registry.items.spawneggs;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.locustswarm.LocustSwarmEntity;
import io.github.GrassyDev.pvzmod.registry.items.seedpackets.SeedItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LocustswarmEgg extends SeedItem {
    public LocustswarmEgg(Settings settings) {
        super(settings);
    }

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);

		tooltip.add(Text.translatable("item.pvzmod.creative")
				.formatted(Formatting.UNDERLINE));
	}

	public ActionResult useOnBlock(ItemUsageContext context) {
		Direction direction = context.getSide();
		if (direction == Direction.DOWN) {
			return ActionResult.FAIL;
		}
		else if (direction == Direction.SOUTH) {
			return ActionResult.FAIL;
		}
		else if (direction == Direction.EAST) {
			return ActionResult.FAIL;
		}
		else if (direction == Direction.NORTH) {
			return ActionResult.FAIL;
		}
		else if (direction == Direction.WEST) {
			return ActionResult.FAIL;
		}
		else {
			World world = context.getWorld();
			ItemPlacementContext itemPlacementContext = new ItemPlacementContext(context);
			BlockPos blockPos = itemPlacementContext.getBlockPos();
			ItemStack itemStack = context.getStack();
			Vec3d vec3d = Vec3d.ofBottomCenter(blockPos);
			Box box = PvZEntity.LOCUSTSWARM.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ());
			if (world.isSpaceEmpty((Entity)null, box) && (world.getOtherEntities((Entity) null, box)).isEmpty()) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					LocustSwarmEntity zombieEntity = (LocustSwarmEntity) PvZEntity.LOCUSTSWARM.create(serverWorld, itemStack.getNbt(), (Text) null, context.getPlayer(), blockPos, SpawnReason.SPAWN_EGG, true, true);
					if (zombieEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(context.getPlayerYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					zombieEntity.refreshPositionAndAngles(zombieEntity.getX(), zombieEntity.getY(), zombieEntity.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(zombieEntity);
					world.playSound((PlayerEntity) null, zombieEntity.getX(), zombieEntity.getY(), zombieEntity.getZ(), SoundEvents.ENTITY_SILVERFISH_AMBIENT, SoundCategory.BLOCKS, 0.6f, 0.8F);
				}
				return ActionResult.success(world.isClient);
			}
			else {
				return ActionResult.FAIL;
			}
		}
	}
}
