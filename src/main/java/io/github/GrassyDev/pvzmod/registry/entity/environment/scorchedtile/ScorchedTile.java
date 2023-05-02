package io.github.GrassyDev.pvzmod.registry.entity.environment.scorchedtile;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.modernday.ImpEntity;
import io.github.GrassyDev.pvzmod.registry.items.seedpackets.IcebergLettuceSeeds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;

import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PLANT_LOCATION;
import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class ScorchedTile extends TileEntity {

	public boolean hasPlant;
	protected int dragoTick = 360;
	public ScorchedTile(EntityType<? extends TileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 115) {
			Vec3d particlePos = Vec3d.ofCenter(this.getBlockPos());
			RandomGenerator randomGenerator = this.getRandom();
			for(int i = 0; i < 16; ++i) {
				double d = this.random.nextDouble() / 10 * this.random.range(-1, 1);
				double e = this.random.nextDouble() / 10 * this.random.range(0, 1);
				double f = this.random.nextDouble() / 10 * this.random.range(-1, 1);
				this.world.addParticle(ParticleTypes.LARGE_SMOKE, particlePos.getX(), particlePos.getY(), particlePos.getZ(), d, e, f);
				this.world.addParticle(ParticleTypes.LARGE_SMOKE, particlePos.getX(), particlePos.getY(), particlePos.getZ(), d, e, f);
				this.world.addParticle(ParticleTypes.SMOKE, particlePos.getX(), particlePos.getY() + this.random.range(0, 1), particlePos.getZ(), d, e, f);
				this.world.addParticle(ParticleTypes.FLAME, particlePos.getX(), particlePos.getY() + this.random.range(0, 1), particlePos.getZ(), d, e, f);
			}
			for(int i = 0; i < 8; ++i) {
				double e = this.random.nextDouble() / 10 * (this.random.range(0, 1));
				this.world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, particlePos.getX() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F),
						particlePos.getY(),
						particlePos.getZ()  + (double)MathHelper.nextBetween(randomGenerator,
								-0.5F, 0.5F), 0, e, 0);
			}
		}
	}

	public void createImp(BlockPos blockPos, Vec3d vec3d){
		if (this.world instanceof ServerWorld serverWorld) {
			ImpEntity zombie = (ImpEntity) PvZEntity.IMPDRAGON.create(world);
			zombie.refreshPositionAndAngles(vec3d.getX(), vec3d.getY(), vec3d.getZ(), 0, 0);
			zombie.initialize(serverWorld, world.getLocalDifficulty(blockPos), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
			zombie.setPersistent();
			zombie.setHeadYaw(0);
			serverWorld.spawnEntityAndPassengers(zombie);
		}
	}

	@Override
	public void tick() {
		super.tick();
		List<PlantEntity> list = world.getNonSpectatingEntities(PlantEntity.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(this.getX(), this.getY(), this.getZ()));
		this.hasPlant = false;
		for (PlantEntity plantEntity : list) {
			if (!PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("flying")){
				hasPlant = true;
			}
		}


		if (--dragoTick <= 0){
			if (!hasPlant){
				createImp(this.getBlockPos(), Vec3d.ofCenter(this.getBlockPos()));
				this.world.sendEntityStatus(this, (byte) 115);
			}
			dragoTick = 360;
		}

		if (this.age >= 1200){
			this.discard();
		}


	}


	/** /~*~//~*INTERACTION*~//~*~/ **/



	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		Item item = itemStack.getItem();
		if (itemStack.isOf(ModItems.ICEBERGLETTUCE_SEED_PACKET) && !player.getItemCooldownManager().isCoolingDown(item)) {
			this.playSound(PvZCubed.ICEBERGEXPLOSIONEVENT);
			this.remove(RemovalReason.DISCARDED);
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
				if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
					player.getItemCooldownManager().set(ModItems.ICEBERGLETTUCE_SEED_PACKET, IcebergLettuceSeeds.cooldown);
				}
			}
			return ActionResult.SUCCESS;
		}
		else {
			return super.interactMob(player, hand);
		}
	}
}
