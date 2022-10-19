package io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity;

import net.minecraft.entity.ai.goal.MeleeAttackGoal;

public class HypnoBerserkerAttackGoal extends MeleeAttackGoal {
   private final HypnoBerserkerEntity pvzombie;
   private int ticks;

   public HypnoBerserkerAttackGoal(HypnoBerserkerEntity pvzombie, double speed, boolean pauseWhenMobIdle) {
      super(pvzombie, speed, pauseWhenMobIdle);
      this.pvzombie = pvzombie;
   }

   public void start() {
      super.start();
      this.ticks = 0;
   }

   public void stop() {
      super.stop();
      this.pvzombie.setAttacking(false);
   }

   public void tick() {
      super.tick();
      ++this.ticks;
      if (this.ticks >= 5 && this.getCooldown() < this.getMaxCooldown() / 2) {
         this.pvzombie.setAttacking(true);
      } else {
         this.pvzombie.setAttacking(false);
      }

   }
}