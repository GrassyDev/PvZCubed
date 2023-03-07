package io.github.GrassyDev.pvzmod.registry.entity.gravestones;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

import java.util.List;

public abstract class GraveEntity extends PathAwareEntity implements Monster {

	/** For Zombies that can summon other zombies**/

	protected int spellTicks;

	private static final TrackedData<Byte> SPELL;
	private GraveEntity.Spell spell;

	protected GraveEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
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

	protected boolean isDisallowedInPeaceful() {
		return true;
	}

	static {
		SPELL = DataTracker.registerData(GraveEntity.class, TrackedDataHandlerRegistry.BYTE);
	}

	public void tick() {
		super.tick();
		if (this.hasStatusEffect(PvZCubed.HYPNOTIZED)){
			this.removeStatusEffect(PvZCubed.HYPNOTIZED);
		}
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

		public static GraveEntity.Spell byId(int id) {
			GraveEntity.Spell[] var1 = values();
			int var2 = var1.length;

			for(int var3 = 0; var3 < var2; ++var3) {
				GraveEntity.Spell spell = var1[var3];
				if (id == spell.id) {
					return spell;
				}
			}

			return NONE;
		}
	}

	@Override
	public boolean isPushable() {
		return false;
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

	public void setSpell(GraveEntity.Spell spell) {
		this.spell = spell;
		this.dataTracker.set(SPELL, (byte)spell.id);
	}

	protected GraveEntity.Spell getSpell() {
		return !this.world.isClient ? this.spell : GraveEntity.Spell.byId((Byte)this.dataTracker.get(SPELL));
	}

	protected void mobTick() {
		super.mobTick();
		if (this.spellTicks > 0) {
			--this.spellTicks;
		}

	}

	public static boolean checkVillager(Vec3d pos, ServerWorldAccess world) {
		List<VillagerEntity> list = world.getNonSpectatingEntities(VillagerEntity.class, PvZEntity.BASICGRAVESTONE.getDimensions().getBoxAt(pos).expand(30));
		return !list.isEmpty();
	}

	public static boolean checkPlant(Vec3d pos, ServerWorldAccess world) {
		List<PlantEntity> list = world.getNonSpectatingEntities(PlantEntity.class, PvZEntity.BASICGRAVESTONE.getDimensions().getBoxAt(pos).expand(5));
		return !list.isEmpty();
	}
}
