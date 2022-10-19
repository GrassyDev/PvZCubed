package io.github.GrassyDev.pvzmod.mixin;


import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
	@Inject(method = "init", at = @At("TAIL"))
	public void pvzCubed$onInit(CallbackInfo ci) {
		PvZCubed.LOGGER.info("This line is printed by an example mod mixin!");
	}
}
