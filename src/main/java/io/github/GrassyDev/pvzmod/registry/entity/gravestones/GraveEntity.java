package io.github.GrassyDev.pvzmod.registry.entity.gravestones;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.GardenEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.gravebuster.GravebusterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.graves.GraveDifficulty;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZPOISON;
import static io.github.GrassyDev.pvzmod.PvZCubed.ZOMBIE_MATERIAL;

public abstract class GraveEntity extends PathAwareEntity implements Monster {

	/** For Zombies that can summon other zombies**/

	protected float difficultymodifier = 0;
	protected float halfModifier = 1;

	protected int spellTicks;

	public boolean beingEaten = false;

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

	@Override
	protected void dropLoot(DamageSource source, boolean causedByPlayer) {
		if (this.world.getGameRules().getBoolean(PvZCubed.SHOULD_ZOMBIE_DROP)){
			super.dropLoot(source, causedByPlayer);
		}
	}

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(GraveEntity.class, TrackedDataHandlerRegistry.INTEGER);

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(ONE_TAG, false);
		this.dataTracker.startTracking(HALF_TAG, false);
		this.dataTracker.startTracking(INFINITE_TAG, false);
		this.dataTracker.startTracking(UNLOCK_TAG, false);
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
		this.dataTracker.startTracking(SPELL, (byte)0);
	}
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(ONE_TAG, tag.getBoolean("is1x1"));
		this.dataTracker.set(HALF_TAG, tag.getBoolean("half"));
		this.dataTracker.set(INFINITE_TAG, tag.getBoolean("isInfinite"));
		this.dataTracker.set(UNLOCK_TAG, tag.getBoolean("isUnlocked"));
		//Variant//
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
		this.spellTicks = tag.getInt("SpellTicks");
	}

	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putBoolean("is1x1", this.is1x1());
		tag.putBoolean("half", this.isHalf());
		tag.putBoolean("isInfinite", this.isInfinite());
		tag.putBoolean("isUnlocked", this.isUnlock());
		//Variant//
		tag.putInt("Variant", this.getTypeVariant());
		tag.putInt("SpellTicks", this.spellTicks);
	}

	static {
		SPELL = DataTracker.registerData(GraveEntity.class, TrackedDataHandlerRegistry.BYTE);
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		if (this.isInfinite()){
			this.setPersistent();
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public GraveDifficulty getVariant() {
		return GraveDifficulty.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(GraveDifficulty variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
	}


	//Half Tag

	protected static final TrackedData<Boolean> HALF_TAG =
			DataTracker.registerData(GraveEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum HalfSpawn {
		FALSE(false),
		TRUE(true);

		HalfSpawn(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean isHalf() {
		return this.dataTracker.get(HALF_TAG);
	}

	public void setHalfSpawn(GraveEntity.HalfSpawn halfSpawn) {
		this.dataTracker.set(HALF_TAG, halfSpawn.getId());
	}


	//1x1 Tag

	protected static final TrackedData<Boolean> ONE_TAG =
			DataTracker.registerData(GraveEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum OnexOne {
		FALSE(false),
		TRUE(true);

		OnexOne(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean is1x1() {
		return this.dataTracker.get(ONE_TAG);
	}

	public void set1x1(GraveEntity.OnexOne onexOne) {
		this.dataTracker.set(ONE_TAG, onexOne.getId());
	}


	//Infinite Tag

	protected static final TrackedData<Boolean> INFINITE_TAG =
			DataTracker.registerData(GraveEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum Infitie {
		FALSE(false),
		TRUE(true);

		Infitie(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean isInfinite() {
		return this.dataTracker.get(INFINITE_TAG);
	}

	public void setInfinite(GraveEntity.Infitie infinite) {
		this.dataTracker.set(INFINITE_TAG, infinite.getId());
	}


	//Unlock Tag

	protected static final TrackedData<Boolean> UNLOCK_TAG =
			DataTracker.registerData(GraveEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum Unlock {
		FALSE(false),
		TRUE(true);

		Unlock(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean isUnlock() {
		return this.dataTracker.get(UNLOCK_TAG);
	}

	public void setUnlock(GraveEntity.Unlock unlock) {
		this.dataTracker.set(UNLOCK_TAG, unlock.getId());
	}

	//////////////


	/** /~*~//~*INTERACTION*~//~*~/ **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.EASY)) {
			setVariant(GraveDifficulty.EASY);
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.EASYMED)) {
			setVariant(GraveDifficulty.EASYMED);
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.MED)) {
			setVariant(GraveDifficulty.MED);
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.MEDHARD)) {
			setVariant(GraveDifficulty.MEDHARD);
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.HARD)) {
			setVariant(GraveDifficulty.HARD);
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.SUPERHARD)) {
			setVariant(GraveDifficulty.SUPERHARD);
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.NIGHTMARE)) {
			setVariant(GraveDifficulty.NIGHTMARE);
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.CRAAAAZY)) {
			setVariant(GraveDifficulty.CRAAAZY);
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.ONEBYONE)) {
			set1x1(OnexOne.TRUE);
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.HALF)) {
			setHalfSpawn(HalfSpawn.TRUE);
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.INFINITE)) {
			setInfinite(Infitie.TRUE);
			this.setPersistent();
			return ActionResult.SUCCESS;
		}
		else if (itemStack.isOf(ModItems.UNLOCK)) {
			setUnlock(Unlock.TRUE);
			return ActionResult.SUCCESS;
		}
		else {
			return ActionResult.PASS;
		}
	}

	public EntityType<? extends GraveEntity> entityBox = PvZEntity.BASICGRAVESTONE;


	public <T extends LivingEntity> T getClosestGarden(List<? extends T> entityList, TargetPredicate targetPredicate, @Nullable LivingEntity entity, double x, double y, double z) {
		double d = -1.0;
		T livingEntity = null;

		for(T livingEntity2 : entityList) {
			if (targetPredicate.test(entity, livingEntity2)) {
				double e = livingEntity2.squaredDistanceTo(x, y, z);
				if (d == -1.0 || e < d) {
					d = e;
					livingEntity = livingEntity2;
				}
			}
		}

		return livingEntity;
	}

	public static List<GardenEntity> checkGarden(Vec3d pos, ServerWorldAccess world) {
		return world.getNonSpectatingEntities(GardenEntity.class, PvZEntity.BASICGRAVESTONE.getDimensions().getBoxAt(pos).expand(50));
	}

	public void tick() {
		if (!this.world.isClient() && this.getTarget() == null) {
			ServerWorldAccess serverWorldAccess = (ServerWorldAccess) this.getWorld();
			if (serverWorldAccess != null) {
				this.setTarget(this.getClosestGarden(checkGarden(this.getPos(), serverWorldAccess), TargetPredicate.DEFAULT, this, this.getX(), this.getY(), this.getZ()));
			}
		}
		if (!(ZOMBIE_MATERIAL.get(this.getType()).orElse("flesh").equals("flesh")) && (this.hasStatusEffect(PVZPOISON) || this.hasStatusEffect(StatusEffects.POISON))){
			this.removeStatusEffect(PVZPOISON);
			this.removeStatusEffect(StatusEffects.POISON);
		}
		super.tick();
		if (this.getWorld().getDifficulty().equals(Difficulty.HARD) && this.getVariant().equals(GraveDifficulty.NONE)){
			difficultymodifier = 0.75f;
		}
		if (this.isHalf()){
			halfModifier = 2;
		}
		List<GravebusterEntity> list = world.getNonSpectatingEntities(GravebusterEntity.class, entityBox.getDimensions().getBoxAt(this.getX(), this.getY(), this.getZ()));
		this.beingEaten = !list.isEmpty();
	}

	public boolean canWalkOnFluid(FluidState state) {
		return state.isIn(FluidTags.WATER);
	}

	protected boolean shouldSwimInFluids() {
		return true;
	}

	@Override
	public void onDeath(DamageSource source) {
		super.onDeath(source);
		super.discard();
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
		List<PlantEntity> list = world.getNonSpectatingEntities(PlantEntity.class, PvZEntity.BASICGRAVESTONE.getDimensions().getBoxAt(pos).expand(15));
		return !list.isEmpty();
	}
}
