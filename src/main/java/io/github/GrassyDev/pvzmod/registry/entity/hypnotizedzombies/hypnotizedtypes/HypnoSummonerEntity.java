package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedtypes;

import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

public abstract class HypnoSummonerEntity extends SpellcastingIllagerEntity {
	/** For Hypnotized Zombies that can summon other zombies**/
	protected HypnoSummonerEntity(EntityType<? extends SpellcastingIllagerEntity> entityType, World world) {
		super(entityType, world);
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

	@Override
	protected SoundEvent getCastSpellSound() {
		return null;
	}

	@Override
	public void addBonusForWave(int wave, boolean unused) {

	}

	@Override
	public SoundEvent getCelebratingSound() {
		return null;
	}

	@Override
	protected boolean isDisallowedInPeaceful() {
		return false;
	}

	@Override
	public boolean isAngryAt(PlayerEntity player) {
		return false;
	}

	@Override
	public boolean canLead() {
		return false;
	}

	@Override
	public void onDeath(DamageSource source){

	}

	public EntityGroup getGroup() {
		return EntityGroup.DEFAULT;
	}
}
