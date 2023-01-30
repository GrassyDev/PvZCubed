package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedtypes;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.duckytube.DuckyTubeEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public abstract class HypnoSummonerEntity extends PathAwareEntity {
	private int despawnDucky;
	private int spawnDucky;

	/** For Hypnotized Zombies that can summon other zombies**/

	protected int spellTicks;

	private static final TrackedData<Byte> SPELL;

	private HypnoSummonerEntity.Spell spell;

	protected HypnoSummonerEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
	}

	public void tick() {
		super.tick();
		Entity vehicle = this.getVehicle();
		if (this.hasStatusEffect(PvZCubed.ICE) && vehicle != null) {
			((DuckyTubeEntity) vehicle).addStatusEffect((new StatusEffectInstance(PvZCubed.ICE, 60, 1)));
			((DuckyTubeEntity) vehicle).addStatusEffect((new StatusEffectInstance(PvZCubed.ICE, 60, 1)));
		} else if (!this.hasStatusEffect(PvZCubed.ICE) && vehicle != null) {
			((DuckyTubeEntity) vehicle).removeStatusEffect(PvZCubed.ICE);
		}
		if (this.hasStatusEffect(PvZCubed.FROZEN) && vehicle instanceof DuckyTubeEntity) {
			vehicle.discard();
			this.discard();
		}
		if (!this.hasStatusEffect(PvZCubed.FROZEN) && vehicle instanceof DuckyTubeEntity) {
			((DuckyTubeEntity) vehicle).removeStatusEffect(PvZCubed.FROZEN);
		}
		if (this.getRandom().nextFloat() < 0.8F && (this.isTouchingWater() || this.isInLava())) {
			this.getJumpControl().setActive();
			this.setSwimming(true);
		} else if (!this.isTouchingWater() || !this.isInLava()) {
			this.setSwimming(false);
		}
		if (this.isInsideWaterOrBubbleColumn()) {
			this.despawnDucky = 0;
		}
		if (!this.isSubmergedInWater() && !this.hasVehicle()) {
			if (this.isTouchingWater()) {
				++this.spawnDucky;
				if (this.spawnDucky == 1) {
					if (world instanceof ServerWorld) {
						DuckyTubeEntity duckyTube = new DuckyTubeEntity(PvZEntity.DUCKYTUBE, this.world);
						duckyTube.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
						world.spawnEntity(duckyTube);
						this.startRiding(duckyTube);
						world.playSound(null, duckyTube.getX(), duckyTube.getY(), duckyTube.getZ(), SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.BLOCKS, 0.33f, 0.8F);
					}
				}
			}
		}
		if (!this.isInsideWaterOrBubbleColumn()) {
			++this.despawnDucky;
			if (this.despawnDucky == 10) {
				this.spawnDucky = 0;
				if (vehicle != null && vehicle.age > 9 && vehicle instanceof DuckyTubeEntity) {
					dismountVehicle();
				}
			}
			if (this.despawnDucky > 10) {
				this.despawnDucky = 0;
			}
		}
		if (vehicle instanceof DuckyTubeEntity) {
			((DuckyTubeEntity) vehicle).setTarget(this.getTarget());
		}
	}

	protected SoundEvent getCastSpellSound() {
		return PvZCubed.ENTITYRISINGEVENT;
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
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

		public static HypnoSummonerEntity.Spell byId(int id) {
			HypnoSummonerEntity.Spell[] var1 = values();
			int var2 = var1.length;

			for(int var3 = 0; var3 < var2; ++var3) {
				HypnoSummonerEntity.Spell spell = var1[var3];
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

	public void setSpell(HypnoSummonerEntity.Spell spell) {
		this.spell = spell;
		this.dataTracker.set(SPELL, (byte)spell.id);
	}

	protected HypnoSummonerEntity.Spell getSpell() {
		return !this.world.isClient ? this.spell : HypnoSummonerEntity.Spell.byId((Byte)this.dataTracker.get(SPELL));
	}

	protected void mobTick() {
		super.mobTick();
		if (this.spellTicks > 0) {
			--this.spellTicks;
		}

	}

	protected class AttackGoal extends MeleeAttackGoal {
		public AttackGoal() {
			super(HypnoSummonerEntity.this, 1.0, true);
		}

		protected double getSquaredMaxAttackDistance(LivingEntity entity) {
			float f = HypnoSummonerEntity.this.getWidth() - 0.1F;
			return (double)(f * 4.0F * f * 4.0F + entity.getWidth());
		}
	}
}
