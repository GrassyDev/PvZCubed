package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes;

import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class SummonerEntity extends GeneralPvZombieEntity implements Monster {

	/** For Zombies that can summon other zombies**/

	protected int spellTicks;

	private static final TrackedData<Byte> SPELL;
	private SummonerEntity.Spell spell;

	protected SummonerEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	protected SoundEvent getCastSpellSound() {
		return PvZSounds.ENTITYRISINGEVENT;
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}


	protected int getSpellTicks() {
		return this.spellTicks;
	}

	static {
		SPELL = DataTracker.registerData(SummonerEntity.class, TrackedDataHandlerRegistry.BYTE);
	}



	/** /~*~//~*VARIANTS*~//~*~/ **/

	//Charm Counter

	private static final TrackedData<Integer> SUMMON_TIMES =
			DataTracker.registerData(SummonerEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		//Set Count
		setCount(0);
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	public int getTypeCount() {
		return this.dataTracker.get(SUMMON_TIMES);
	}

	public void setCount(Integer count) {
		this.dataTracker.set(SUMMON_TIMES, count);
	}

	public void addCount(){
		int count = getTypeCount();
		this.dataTracker.set(SUMMON_TIMES, count + 1);
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
		this.dataTracker.startTracking(SUMMON_TIMES, 0);
		this.dataTracker.startTracking(SPELL, (byte)0);
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.spellTicks = nbt.getInt("SpellTicks");
		this.dataTracker.set(SUMMON_TIMES, nbt.getInt("Count"));
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("SpellTicks", this.spellTicks);
		nbt.putInt("Count", this.getTypeCount());
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
