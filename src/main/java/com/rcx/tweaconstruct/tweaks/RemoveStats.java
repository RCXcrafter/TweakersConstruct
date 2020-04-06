package com.rcx.tweaconstruct.tweaks;

import java.util.ArrayList;
import java.util.List;

import com.rcx.tweaconstruct.ConfigHandler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import slimeknights.tconstruct.library.events.MaterialEvent.IntegrationEvent;
import slimeknights.tconstruct.library.events.MaterialEvent.MaterialRegisterEvent;
import slimeknights.tconstruct.library.events.MaterialEvent.StatRegisterEvent;
import slimeknights.tconstruct.library.materials.IMaterialStats;

public class RemoveStats {
	public static void preInit() {
		if (ConfigHandler.RemoveStatList.length > 0)
			MinecraftForge.EVENT_BUS.register(new RemoveStats());
	}

	@SubscribeEvent
	public void onStatRegistered(StatRegisterEvent event) {
		IMaterialStats stats = event.newStats != null ? event.newStats:event.stats;
		for(String materialStat : ConfigHandler.RemoveStatList) {


			if(event.material != null && stats != null && materialStat.equals(event.material.getIdentifier() + ":" + stats.getIdentifier())) {
				event.setCanceled(true);
			}
		}
	}
}
