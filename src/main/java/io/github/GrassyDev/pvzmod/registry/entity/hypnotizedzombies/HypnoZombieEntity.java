package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies;

import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.world.World;

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
