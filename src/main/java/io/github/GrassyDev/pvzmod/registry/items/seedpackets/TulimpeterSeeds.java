package io.github.GrassyDev.pvzmod.registry.items.seedpackets;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.environment.cratertile.CraterTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.scorchedtile.ScorchedTile;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.peashooter.PeashooterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.tulimpeter.TulimpeterEntity;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class TulimpeterSeeds extends SeedItem implements FabricItem {
	public boolean used;
	public static int cooldown = (int) (PVZCONFIG.nestedSeeds.moreSeeds.tulimpeterS() * 20);

	public TulimpeterSeeds(Settings settings) {
		super(settings);
	}

	@Override
	public boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
		return false;
	}


	public static final String COOL_KEY = "Cooldown";

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);
		NbtCompound nbtCompound = stack.getOrCreateNbt();
		if (entity instanceof PlayerEntity player) {
			if (player.getItemCooldownManager().getCooldownProgress(this, 0) > 0.0f) {
				nbtCompound.putFloat("Cooldown", player.getItemCooldownManager().getCooldownProgress(this, 0));
			} else if (nbtCompound.getFloat("Cooldown") > 0.1f && player.getItemCooldownManager().getCooldownProgress(this, 0) <= 0.0f) {
				float progress = nbtCompound.getFloat("Cooldown");
				player.getItemCooldownManager().set(this, (int) Math.floor(cooldown * progress));
			}
			if (!player.getItemCooldownManager().isCoolingDown(this) && (nbtCompound.getFloat("Cooldown") != 0 || nbtCompound.get("Cooldown") == null)) {
				nbtCompound.putFloat("Cooldown", 0);
			}
		}
	}

	//Credits to Patchouli for the tooltip code!
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);

		tooltip.add(Text.translatable("item.pvzmod.seed_packet.enchant.family")
				.formatted(Formatting.LIGHT_PURPLE));

		tooltip.add(Text.translatable("item.pvzmod.tulimpeter_seed_packet.flavour")
				.formatted(Formatting.DARK_GRAY));
	}

	public ActionResult useOnBlock(ItemUsageContext context) {
		Direction direction = context.getSide();
		if (direction == Direction.DOWN) {
			return ActionResult.FAIL;
		} else if (direction == Direction.SOUTH) {
			return ActionResult.FAIL;
		} else if (direction == Direction.EAST) {
			return ActionResult.FAIL;
		} else if (direction == Direction.NORTH) {
			return ActionResult.FAIL;
		} else if (direction == Direction.WEST) {
			return ActionResult.FAIL;
		} else {
			World world = context.getWorld();
			ItemPlacementContext itemPlacementContext = new ItemPlacementContext(context);
			BlockPos blockPos = itemPlacementContext.getBlockPos();
			ItemStack itemStack = context.getStack();
			Vec3d vec3d = Vec3d.ofBottomCenter(blockPos);
			Box box = PvZEntity.TULIMPETER.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ());
			if (world.isSpaceEmpty((Entity)null, box) && world instanceof ServerWorld serverWorld) {
				TulimpeterEntity plantEntity = (TulimpeterEntity) PvZEntity.TULIMPETER.create(serverWorld, itemStack.getNbt(), (Text) null, context.getPlayer(), blockPos, SpawnReason.SPAWN_EGG, true, true);
				List<PlantEntity> list = world.getNonSpectatingEntities(PlantEntity.class, PvZEntity.TULIMPETER.getDimensions().getBoxAt(plantEntity.getPos()));
				if (list.isEmpty()) {
					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(context.getPlayerYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), f, 0.0F);
					world.spawnEntity(plantEntity);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), PvZSounds.PLANTPLANTEDEVENT, SoundCategory.BLOCKS, 0.6f, 0.8F);


					PlayerEntity user = context.getPlayer();
					if (!user.getAbilities().creativeMode) {
						if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
						if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
							user.getItemCooldownManager().set(this, cooldown);
						}
					}
					return ActionResult.success(world.isClient);
				} else {
					return ActionResult.FAIL;
				}
			} else {
				return ActionResult.PASS;
			}
		}
	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		World world = user.getWorld();
		BlockPos blockPos = entity.getBlockPos();
		SoundEvent sound = null;
		if (world instanceof ServerWorld serverWorld && entity instanceof TileEntity
				&& !(entity instanceof ScorchedTile)
				&& !(entity instanceof CraterTile)) {
			TulimpeterEntity plantEntity = PvZEntity.TULIMPETER.create(serverWorld, stack.getNbt(), (Text) null, user, blockPos, SpawnReason.SPAWN_EGG, true, true);
			List<PlantEntity> list = world.getNonSpectatingEntities(PlantEntity.class, PvZEntity.TULIMPETER.getDimensions().getBoxAt(plantEntity.getPos()));
			if (list.isEmpty()) {
				float f = (float) MathHelper.floor((MathHelper.wrapDegrees(user.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
				plantEntity.refreshPositionAndAngles(entity.getX(), entity.getY(), entity.getZ(), f, 0.0F);
				world.spawnEntity(plantEntity);
				world.playSound((PlayerEntity) null, entity.getX(), entity.getY(), entity.getZ(), PvZSounds.PLANTPLANTEDEVENT, SoundCategory.BLOCKS, 0.6f, 0.8F);

				if (!user.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
						stack.decrement(1);
					}
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						user.getItemCooldownManager().set(this, cooldown);
					}
				}
				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.FAIL;
			}
		} else if (world instanceof ServerWorld serverWorld && entity instanceof LilyPadEntity lilyPadEntity) {
			if (lilyPadEntity.onWater) {
				sound = SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED;
			} else {
				sound = PvZSounds.PLANTPLANTEDEVENT;
			}
			lilyPadEntity.setPuffshroomPermanency(LilyPadEntity.PuffPermanency.PERMANENT);
			TulimpeterEntity plantEntity = (TulimpeterEntity) PvZEntity.TULIMPETER.create(serverWorld, stack.getNbt(), (Text) null, user, entity.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
			if (plantEntity == null) {
				return ActionResult.FAIL;
			}

			float f = (float) MathHelper.floor((MathHelper.wrapDegrees(user.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
			plantEntity.refreshPositionAndAngles(entity.getX(), entity.getY(), entity.getZ(), f, 0.0F);
			((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
			plantEntity.rideLilyPad(entity);
			world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, 0.6f, 0.8F);
			if (!user.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
					stack.decrement(1);
				}
				if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
					user.getItemCooldownManager().set(this, cooldown);
				}
			}
			return ActionResult.success(world.isClient);
		} else {
			return ActionResult.PASS;
		}
	}
}