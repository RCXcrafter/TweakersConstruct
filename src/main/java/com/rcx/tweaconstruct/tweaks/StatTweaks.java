package com.rcx.tweaconstruct.tweaks;

import java.awt.List;
import java.util.ArrayList;

import com.rcx.tweaconstruct.ConfigHandler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.Event;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.events.MaterialEvent;
import slimeknights.tconstruct.library.materials.BowMaterialStats;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.IMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.MaterialTypes;

public class StatTweaks {
	public static void postInit() {
		if (ConfigHandler.statTweaksList.length == 0)
			return;

		for (String entry : ConfigHandler.statTweaksList) {
			String[] entries = entry.split(":");
			if (entries.length != 11)
				continue;

			Material material = TinkerRegistry.getMaterial(entries[0]);

			if (material == null)
				continue;

			HeadMaterialStats headStats = material.getStats(MaterialTypes.HEAD);
			HandleMaterialStats handleStats = material.getStats(MaterialTypes.HANDLE);
			ExtraMaterialStats extraStats = material.getStats(MaterialTypes.EXTRA);
			BowMaterialStats bowStats = material.getStats(MaterialTypes.BOW);

			if(headStats != null)
				tweakMaterialStats(material, new HeadMaterialStats(entries[1].equals("d") ? headStats.durability:Integer.parseInt(entries[1]), entries[2].equals("d") ? headStats.miningspeed:Float.parseFloat(entries[2]), entries[3].equals("d") ? headStats.attack:Float.parseFloat(entries[3]), entries[4].equals("d") ? headStats.harvestLevel:Integer.parseInt(entries[4])));
			if(handleStats != null)
				tweakMaterialStats(material, new HandleMaterialStats(entries[5].equals("d") ? handleStats.modifier:Float.parseFloat(entries[5]), entries[6].equals("d") ? handleStats.durability:Integer.parseInt(entries[6])));
			if(extraStats != null)
				tweakMaterialStats(material, new ExtraMaterialStats(entries[7].equals("d") ? extraStats.extraDurability:Integer.parseInt(entries[7])));
			if(bowStats != null)
				tweakMaterialStats(material, new BowMaterialStats(entries[8].equals("d") ? bowStats.drawspeed:Float.parseFloat(entries[8]), entries[9].equals("d") ? bowStats.range:Float.parseFloat(entries[9]), entries[10].equals("d") ? bowStats.bonusDamage:Float.parseFloat(entries[10])));
		}
	}

	public static void tweakMaterialStats(Material material, IMaterialStats stats) {
		MaterialEvent.StatRegisterEvent<?> event = new MaterialEvent.StatRegisterEvent<IMaterialStats>(material, stats);
		MinecraftForge.EVENT_BUS.post(event);

		// overridden stats from event
		if(event.getResult() == Event.Result.ALLOW) {
			stats = event.newStats;
		}

		material.addStats(stats);
	}
}
