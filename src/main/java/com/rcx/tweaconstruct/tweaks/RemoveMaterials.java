package com.rcx.tweaconstruct.tweaks;

import com.rcx.tweaconstruct.ConfigHandler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.MaterialEvent.IntegrationEvent;
import slimeknights.tconstruct.library.events.MaterialEvent.MaterialRegisterEvent;

public class RemoveMaterials {
	public static void preInit() {
		if (ConfigHandler.RemoveMaterialList.length > 0)
			MinecraftForge.EVENT_BUS.register(new RemoveMaterials());
	}
	
	@SubscribeEvent
	public void onMaterialRegistered(MaterialRegisterEvent event) {
		for(String materialName : ConfigHandler.RemoveMaterialList) {
			if(event.material.identifier.equals(materialName)) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent //for some reason this is different :/
	public void onMaterialIntegrated(IntegrationEvent event) {
		if(event.material == null)
			return;

		for(String materialName : ConfigHandler.RemoveMaterialList) {
			if(event.material.identifier.equals(materialName)) {
				event.setCanceled(true);
			}
		}
	}
}
