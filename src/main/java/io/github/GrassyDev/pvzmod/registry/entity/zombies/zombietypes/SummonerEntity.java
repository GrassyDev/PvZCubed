package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.duckytube.DuckyTubeEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public abstract class SummonerEntity extends HostileEntity implements Monster {
	private int despawnDucky;

	/** For Zombies that can summon other zombies**/

	protected int spellTicks;

	private static final TrackedData<Byte> SPELL;
	private SummonerEntity.Spell spell;

	protected SummonerEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	protected SoundEvent getCastSpellSound() {
		return PvZCubed.ENTITYRISINGEVENT;
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	public void tick() {
		super.tick();
		Entity vehicle = this.getVehicle();
		if (this.isInsideWaterOrBubbleColumn()){
			this.despawnDucky = 0;
		}
		if (this.getRandom().nextFloat() < 0.8F && (this.isTouchingWater() || this.isInLava())) {
			this.getJumpControl().setActive();
			this.setSwimming(true);
		}
		else if (!this.isTouchingWater() || !this.isInLava()){
			this.setSwimming(false);
		}
		if (!this.isSubmergedInWater() && !this.hasVehicle()){
			if (this.isTouchingWater()) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					DuckyTubeEntity duckyTube = new DuckyTubeEntity(PvZEntity.DUCKYTUBE, this.world);
					duckyTube.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
					world.spawnEntity(duckyTube);
					this.startRiding(duckyTube);
					world.playSound((PlayerEntity) null, duckyTube.getX(), duckyTube.getY(), duckyTube.getZ(), SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.BLOCKS, 0.33f, 0.8F);
				}
			}
		}
		if (!this.isInsideWaterOrBubbleColumn() && vehicle != null && ++this.despawnDucky > 10){
			if (vehicle.isOnGround()){
				vehicle.discard();
			}
		}
		if (vehicle instanceof DuckyTubeEntity){
			((DuckyTubeEntity) vehicle).setTarget(this.getTarget());
		}
	}

	protected int getSpellTicks() {
		return this.spellTicks;
	}

	static {
		SPELL = DataTracker.registerData(SpellcastingIllagerEntity.class, TrackedDataHandlerRegistry.BYTE);
	}
	protected enum Spell {
		NONE(0, 0.0, 0.0, 0.0),
		SUMMON_VEX(1, 0.7, 0.7, 0.8);

		final int id;
		public final double[] particleVelocity;

		private Spell(int id, double particleVelocityX, double particleVelocityY, double particleVelocityZ) {
			this.id = id;
			this.particleVelocity = new double[]{particleVelocityX, particleVelocityY, particleVelocityZ};
		}

		public static SummonerEntity.Spell byId(int id) {
			SummonerEntity.Spell[] var1 = values();
			int var2 = var1.length;

			for(int var3 = 0; var3 < var2; ++var3) {
				SummonerEntity.Spell spell = var1[var3];
				if (id == spell.id) {
					return spell;
				}
			}

			return NONE;
		}
	}

	protected void pushAway(Entity entity) {
	}
	public boolean damage(DamageSource source, float amount) {
		if (!super.damage(source, amount)) {
			return false;
		} else if (!(this.world instanceof ServerWorld)) {
			return false;
		} else {
			ServerWorld serverWorld = (ServerWorld)this.world;
			LivingEntity livingEntity = this.getTarget();
			if (livingEntity == null && source.getAttacker() instanceof LivingEntity) {
				livingEntity = (LivingEntity)source.getAttacker();
			}
			return true;
		}
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(SPELL, (byte)0);
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.spellTicks = nbt.getInt("SpellTicks");
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("SpellTicks", this.spellTicks);
	}

	public boolean isSpellcasting() {
		if (this.world.isClient) {
			return (Byte)this.dataTracker.get(SPELL) > 0;
		} else {
			return this.spellTicks > 0;
		}
	}

	public void setSpell(SummonerEntity.Spell spell) {
		this.spell = spell;
		this.dataTracker.set(SPELL, (byte)spell.id);
	}

	protected SummonerEntity.Spell getSpell() {
		return !this.world.isClient ? this.spell : SummonerEntity.Spell.byId((Byte)this.dataTracker.get(SPELL));
	}

	protected void mobTick() {
		super.mobTick();
		if (this.spellTicks > 0) {
			--this.spellTicks;
		}

	}
}
