package io.github.GrassyDev.pvzmod.registry.items.seedpackets;

import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;

public abstract class SeedItem extends Item {
	public SeedItem(Item.Settings settings) {
		super(settings);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);
		NbtCompound nbtCompound = stack.getOrCreateNbt();
		if (entity instanceof PlayerEntity player) {
			if (selected){
				int smallSuns = 0;
				int firstSmallSun = -1;
				int largeSunSlot = -1;
				int smallSunSlot = -1;
				if (player.isSneaking()){
					for (int x = 0; x < player.getInventory().size() -1; ++x){
						for (int u = 0; u < player.getInventory().size() -1; ++u){
							if (player.getInventory().getStack(u).isItemEqual(ModItems.SUN.getDefaultStack())){
								if (player.getInventory().getStack(u).getCount() <= player.getInventory().getStack(u).getMaxCount() - 2){
									largeSunSlot = u;
								}
								if (player.getInventory().getStack(u).getCount() < player.getInventory().getStack(u).getMaxCount()){
									smallSunSlot = u;
								}
							}
							if (largeSunSlot != -1) {
								if (player.getInventory().getStack(largeSunSlot).getCount() > player.getInventory().getStack(largeSunSlot).getMaxCount() - 2) {
									largeSunSlot = -1;
								}
							}
							if (smallSunSlot != -1) {
								if (player.getInventory().getStack(smallSunSlot).getCount() == player.getInventory().getStack(smallSunSlot).getMaxCount()) {
									smallSunSlot = -1;
								}
							}
						}
						if (player.getInventory().getStack(x).isItemEqual(ModItems.SMALLSUN.getDefaultStack()) && (player.getInventory().getEmptySlot() != -1 || largeSunSlot != -1)){
							if (player.getInventory().getStack(x).getCount() >=2){
								player.getInventory().removeStack(x, 2);
								player.getInventory().insertStack(ModItems.SUN.getDefaultStack());
							}
							else {
								++smallSuns;
							}
							if (smallSuns >= 2){
								player.getInventory().removeStack(firstSmallSun, 1);
								player.getInventory().removeStack(x, 1);
								smallSuns = 0;
								firstSmallSun = -1;
								player.getInventory().insertStack(ModItems.SUN.getDefaultStack());
							}
							else if (firstSmallSun == -1) {
								firstSmallSun = x;
							}
						}
						if (player.getInventory().getStack(x).isItemEqual(ModItems.LARGESUN.getDefaultStack()) && (player.getInventory().getEmptySlot() != -1 || largeSunSlot != -1)){
							player.getInventory().removeStack(x, 1);
							player.getInventory().insertStack(ModItems.SUN.getDefaultStack());
							player.getInventory().insertStack(ModItems.SUN.getDefaultStack());
						}
					}
				}
			}
			if (player.isCreative()){
				nbtCompound.putFloat("Cooldown", 0);
				player.getItemCooldownManager().set(stack.getItem(), 0);
			}
			if (player.getStackInHand(player.getActiveHand()).isItemEqual(stack) && player.getInventory().getEmptySlot() != -1) {
				Vec3d vec3d = new Vec3d(10, 0.0, 0).rotateY(-entity.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				HitResult hitResult = world.raycast(new RaycastContext(entity.getPos(), entity.getPos().add(vec3d), RaycastContext.ShapeType.VISUAL, RaycastContext.FluidHandling.NONE, entity));
				if (hitResult.getType().equals(HitResult.Type.MISS)) {
					List<Entity> itemEntities = world.getNonSpectatingEntities(Entity.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(hitResult.getPos().getX(), hitResult.getPos().getY(), hitResult.getPos().getZ()).expand(10));
					for (Entity entity1 : itemEntities) {
						if (player.getInventory().getEmptySlot() == -1) {
							break;
						}
						if (entity1 instanceof ItemEntity item && (item.getStack().isItemEqual(ModItems.SUN.getDefaultStack()) ||
								item.getStack().isItemEqual(ModItems.SMALLSUN.getDefaultStack()) ||
								item.getStack().isItemEqual(ModItems.LARGESUN.getDefaultStack()))) {
							player.getInventory().insertStack(item.getStack());
							item.discard();
							player.playSound(PvZSounds.SUNDROPEVENT, 0.5f, 1f);
						}
					}
				}
			}
		}
	}

	public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
		return !miner.isCreative();
	}
}
