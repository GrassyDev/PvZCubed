package io.github.GrassyDev.pvzmod.registry.items.seedpackets;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.peashooter.PeashooterEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PeashooterSeeds extends Item {
    public boolean used;
	public static int cooldown = 100;

    public PeashooterSeeds(Settings settings) {
        super(settings);
    }

	//Credits to Patchouli for the tooltip code!
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);

		tooltip.add(Text.translatable("item.pvzmod.seed_packet.appease.family")
				.formatted(Formatting.GREEN));

		tooltip.add(Text.translatable("item.pvzmod.peashooter_seed_packet.flavour")
				.formatted(Formatting.DARK_GRAY));

		tooltip.add(Text.translatable("item.pvzmod.peashooter_seed_packet.flavour2")
				.formatted(Formatting.DARK_GRAY));
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
            Box box = PvZEntity.PEASHOOTER.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ());
			if (world.isSpaceEmpty((Entity)null, box) && (world.getOtherEntities((Entity) null, box)).isEmpty()) {
                if (world instanceof ServerWorld) {
                    ServerWorld serverWorld = (ServerWorld) world;
                    PeashooterEntity plantEntity = (PeashooterEntity) PvZEntity.PEASHOOTER.create(serverWorld, itemStack.getNbt(), (Text) null, context.getPlayer(), blockPos, SpawnReason.SPAWN_EGG, true, true);
                    if (plantEntity == null) {
                        return ActionResult.FAIL;
                    }

                    float f = (float) MathHelper.floor((MathHelper.wrapDegrees(context.getPlayerYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), f, 0.0F);
                    world.spawnEntity(plantEntity);
                    world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), PvZCubed.PLANTPLANTEDEVENT, SoundCategory.BLOCKS, 0.6f, 0.8F);
                }

                PlayerEntity user = context.getPlayer();
                if (!user.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                    user.getItemCooldownManager().set(this, cooldown);
                }
                return ActionResult.success(world.isClient);
            }
			else {
				return ActionResult.FAIL;
			}
        }
    }

	/**public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		if (entity instanceof LilyPadEntity){
			World world = user.getWorld();
			if (world instanceof ServerWorld) {
				PeashooterEntity peashooterEntity = this.createEntity(world, entity);
				peashooterEntity.setYaw(user.getYaw());
				if (!world.isSpaceEmpty(peashooterEntity, peashooterEntity.getBoundingBox())) {
					return ActionResult.FAIL;
				} else {
					if (!world.isClient) {
						world.spawnEntity(peashooterEntity);
						world.emitGameEvent(user, GameEvent.ENTITY_PLACE, entity.getPos());
						if (!user.getAbilities().creativeMode) {
							stack.decrement(1);
							user.getItemCooldownManager().set(this, 100);
						}
					}

					user.incrementStat(Stats.USED.getOrCreateStat(this));
					return ActionResult.success(world.isClient);
				}
			}
			else {
				return ActionResult.FAIL;
			}
		}
		return null;
	}**/
}
