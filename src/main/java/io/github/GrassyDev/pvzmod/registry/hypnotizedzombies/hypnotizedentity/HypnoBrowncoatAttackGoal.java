package net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity;

import net.minecraft.entity.ai.goal.MeleeAttackGoal;

public class HypnoBrowncoatAttackGoal extends MeleeAttackGoal {
   private final HypnoBrowncoatEntity pvzombie;
   private int ticks;

   public HypnoBrowncoatAttackGoal(HypnoBrowncoatEntity pvzombie, double speed, boolean pauseWhenMobIdle) {
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
      if (this.ticks >= 5 && this.method_28348() < this.method_28349() / 2) {
         this.pvzombie.setAttacking(true);
      } else {
         this.pvzombie.setAttacking(false);
      }

   }
}
