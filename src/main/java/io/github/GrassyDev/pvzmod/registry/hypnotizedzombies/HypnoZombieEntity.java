package io.github.GrassyDev.pvzmod.registry.hypnotizedzombies;

import io.github.GrassyDev.pvzmod.registry.plants.plantentity.ChomperEntity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public abstract class HypnoZombieEntity extends GolemEntity {
	/** For Hypnotized Zombies **/
	protected HypnoZombieEntity(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}

	public EntityGroup getGroup() {
		return EntityGroup.DEFAULT;
	}

	protected class AttackGoal extends MeleeAttackGoal {
		public AttackGoal() {
			super(HypnoZombieEntity.this, 1.0, true);
		}

		protected double getSquaredMaxAttackDistance(LivingEntity entity) {
			float f = HypnoZombieEntity.this.getWidth() - 0.1F;
			return (double)(f * 4.0F * f * 4.0F + entity.getWidth());
		}
	}
}
