package com.rcx.tweaconstruct.tweaks;

import com.rcx.tweaconstruct.ConfigHandler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.TinkerRegisterEvent.ModifierRegisterEvent;

public class Modifiers {
	public static void preInit() {
		if (ConfigHandler.RemoveModifierList.length > 0)
			MinecraftForge.EVENT_BUS.register(new Modifiers());
	}

	@SubscribeEvent
	public void onMaterialRegistered(ModifierRegisterEvent event) {
		for(String modifierName : ConfigHandler.RemoveModifierList) {
			if(event.getRecipe().getIdentifier().equals(modifierName)) {
				event.setCanceled(true);
			}
		}
	}
}
