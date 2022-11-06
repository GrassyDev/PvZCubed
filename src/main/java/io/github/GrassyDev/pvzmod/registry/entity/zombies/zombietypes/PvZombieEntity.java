package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.task.LookAroundTask;
import net.minecraft.entity.ai.brain.task.StayAboveWaterTask;
import net.minecraft.entity.ai.brain.task.WanderAroundTask;
import net.minecraft.entity.ai.brain.task.WardenLookAtEntityTask;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.warden.WardenEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public abstract class PvZombieEntity extends HostileEntity {
	/** For Hypnotized Zombies **/
	protected PvZombieEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		this.getNavigation().setCanSwim(true);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 8.0F);
		this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, 8.0F);
		this.setPathfindingPenalty(PathNodeType.LAVA, 8.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	@Override
	protected void updateDespawnCounter() {
	}
}
