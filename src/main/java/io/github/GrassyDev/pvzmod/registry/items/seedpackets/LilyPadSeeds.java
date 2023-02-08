package io.github.GrassyDev.pvzmod.registry.items.seedpackets;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LilyPadSeeds extends Item {
	public static int cooldown;
    public LilyPadSeeds(Settings settings) {
        super(settings);
    }

	//Credits to Patchouli for the tooltip code!
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);

		tooltip.add(Text.translatable("item.pvzmod.seed_packet.reinforce.family")
				.formatted(Formatting.DARK_BLUE));

		tooltip.add(Text.translatable("item.pvzmod.seed_packet.amphibious.tooltip")
				.formatted(Formatting.UNDERLINE));

		tooltip.add(Text.translatable("item.pvzmod.lilypad_seed_packet.flavour")
				.formatted(Formatting.DARK_GRAY));

		tooltip.add(Text.translatable("item.pvzmod.lilypad_seed_packet.flavour2")
				.formatted(Formatting.DARK_GRAY));

		tooltip.add(Text.translatable("item.pvzmod.lilypad_seed_packet.flavour3")
				.formatted(Formatting.DARK_GRAY));
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		HitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.ANY);
		if (hitResult.getType() == HitResult.Type.MISS) {
			return TypedActionResult.pass(itemStack);
		} else {
			if (hitResult.getType() == HitResult.Type.BLOCK) {
				if (world instanceof ServerWorld) {
					LilyPadEntity lilypadEntity = this.createEntity(world, hitResult);
					lilypadEntity.setPuffshroomPermanency(LilyPadEntity.PuffPermanency.PERMANENT);
					lilypadEntity.setYaw(user.getYaw());
					if (!world.isSpaceEmpty(lilypadEntity, lilypadEntity.getBoundingBox())) {
						return TypedActionResult.fail(itemStack);
					} else {
						if (!world.isClient) {
							world.spawnEntity(lilypadEntity);
							lilypadEntity.setPuffshroomPermanency(LilyPadEntity.PuffPermanency.PERMANENT);
							world.emitGameEvent(user, GameEvent.ENTITY_PLACE, hitResult.getPos());
							FluidState fluidState = world.getFluidState(lilypadEntity.getBlockPos().add(0, -0.25, 0));
							if (fluidState.getFluid() == Fluids.WATER) {
								world.playSound((PlayerEntity) null, lilypadEntity.getX(), lilypadEntity.getY(), lilypadEntity.getZ(), SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED, SoundCategory.BLOCKS, 0.25f, 0.8F);
							} else {
								world.playSound((PlayerEntity) null, lilypadEntity.getX(), lilypadEntity.getY(), lilypadEntity.getZ(), PvZCubed.PLANTPLANTEDEVENT, SoundCategory.BLOCKS, 0.6f, 0.8F);
							}
							if (!user.getAbilities().creativeMode) {
								itemStack.decrement(1);
								user.getItemCooldownManager().set(this, cooldown);
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
