package io.github.GrassyDev.pvzmod.registry.items.seedpackets;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.lilypad.LilyPadEntity;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Iterator;
import java.util.List;

public class LilyPadSeeds extends Item {
    public LilyPadSeeds(Settings settings) {
        super(settings);
    }

    /**public ActionResult useOnBlock(ItemUsageContext context) {
		PlayerEntity user = context.getPlayer();
		World world = context.getWorld();
        Direction direction = context.getSide();

		HitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.ANY);
        if (hitResult.getType() == HitResult.Type.BLOCK || direction == Direction.NORTH) {
			ItemPlacementContext itemPlacementContext = new ItemPlacementContext(context);
			BlockPos blockPos = itemPlacementContext.getBlockPos();
			ItemStack itemStack = context.getStack();
			Vec3d vec3d = Vec3d.ofBottomCenter(blockPos);
			Box box = PvZEntity.LILYPAD.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ());
			FluidState fluidState = world.getFluidState(blockPos);
			FluidState fluidState2 = world.getFluidState(blockPos.up());
			if (fluidState.getFluid() == Fluids.WATER && fluidState2.getFluid() == Fluids.EMPTY) {
				if (world.isSpaceEmpty((Entity) null, box) && world.getOtherEntities((Entity) null, box).isEmpty()) {
					if (world instanceof ServerWorld) {
						ServerWorld serverWorld = (ServerWorld) world;
						LilyPadEntity lilypadEntity = (LilyPadEntity) PvZEntity.LILYPAD.create(serverWorld, itemStack.getNbt(), (Text) null, context.getPlayer(), blockPos, SpawnReason.SPAWN_EGG, true, true);
						if (lilypadEntity == null) {
							return ActionResult.FAIL;
						}

						float f = (float) MathHelper.floor((MathHelper.wrapDegrees(context.getPlayerYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
						lilypadEntity.refreshPositionAndAngles(lilypadEntity.getX(), lilypadEntity.getY(), lilypadEntity.getZ(), f, 0.0F);
						world.spawnEntity(lilypadEntity);
						world.playSound((PlayerEntity) null, lilypadEntity.getX(), lilypadEntity.getY(), lilypadEntity.getZ(), PvZCubed.PLANTPLANTEDEVENT, SoundCategory.BLOCKS, 0.75F, 0.8F);
					}
					if (!user.getAbilities().creativeMode) {
						itemStack.decrement(1);
						user.getItemCooldownManager().set(this, 50);
					}
					return ActionResult.success(world.isClient);
				} else {
					return ActionResult.FAIL;
				}
			}
			else {
				return ActionResult.FAIL;
			}
		}
		else {
			return ActionResult.FAIL;
		}
	}**/

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		HitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.ANY);
		if (hitResult.getType() == HitResult.Type.MISS) {
			return TypedActionResult.pass(itemStack);
		} else {
			/**Vec3d vec3d = user.getRotationVec(1.0F);
			double d = 5.0;
			List<Entity> list = world.getOtherEntities(user, user.getBoundingBox().stretch(vec3d.multiply(5.0)).expand(1.0), RIDERS);
			if (!list.isEmpty()) {
				Vec3d vec3d2 = user.getEyePos();
				Iterator var11 = list.iterator();

				while(var11.hasNext()) {
					Entity entity = (Entity)var11.next();
					Box box = entity.getBoundingBox().expand((double)entity.getTargetingMargin());
					if (box.contains(vec3d2)) {
						return TypedActionResult.pass(itemStack);
					}
				}
			}**/

			if (hitResult.getType() == HitResult.Type.BLOCK) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					LilyPadEntity lilypadEntity = this.createEntity(world, hitResult);
					lilypadEntity.setYaw(user.getYaw());
					if (!world.isSpaceEmpty(lilypadEntity, lilypadEntity.getBoundingBox())) {
						return TypedActionResult.fail(itemStack);
					} else {
						if (!world.isClient) {
							world.spawnEntity(lilypadEntity);
							world.emitGameEvent(user, GameEvent.ENTITY_PLACE, hitResult.getPos());
							if (!user.getAbilities().creativeMode) {
								itemStack.decrement(1);
								user.getItemCooldownManager().set(this, 50);
							}
						}

						user.incrementStat(Stats.USED.getOrCreateStat(this));
						return TypedActionResult.success(itemStack, world.isClient());
					}
				}
			} else {
				return TypedActionResult.pass(itemStack);
			}
		}
		return TypedActionResult.pass(itemStack);
	}

	private LilyPadEntity createEntity(World world, HitResult hitResult) {
		return (LilyPadEntity)(new LilyPadEntity(world, hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z));
	}

}
