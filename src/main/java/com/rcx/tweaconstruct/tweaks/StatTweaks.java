package com.rcx.tweaconstruct.tweaks;

import java.util.HashMap;
import java.util.Map;

import com.rcx.tweaconstruct.ConfigHandler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.MaterialEvent;
import slimeknights.tconstruct.library.materials.BowMaterialStats;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.IMaterialStats;

public class StatTweaks {

	public static Map<String, String[]> materialsToTweak = new HashMap<String, String[]>();

	public static void preInit() {
		MinecraftForge.EVENT_BUS.register(new StatTweaks());

		if (ConfigHandler.statTweaksList.length == 0)
			return;

		for (String entry : ConfigHandler.statTweaksList) {
			String[] entries = entry.split(":");
			if (entries.length != 11)
				continue;

			materialsToTweak.put(entries[0], entries);
		}
	}

	@SubscribeEvent
	public void onStatRegister(MaterialEvent.StatRegisterEvent<IMaterialStats> statRegisterEvent) {
		IMaterialStats newStats = null;

		IMaterialStats oldStats = statRegisterEvent.newStats != null ? statRegisterEvent.newStats:statRegisterEvent.stats;
		HeadMaterialStats headStats = null;
		HandleMaterialStats handleStats = null;
		ExtraMaterialStats extraStats = null;
		BowMaterialStats bowStats = null;

		if (ConfigHandler.durabilityNerf != 100 || ConfigHandler.mineSpeedNerf != 100) {
			if (statRegisterEvent.stats instanceof HeadMaterialStats) {
				headStats = (HeadMaterialStats) oldStats;
				newStats = new HeadMaterialStats(nerfDurability(headStats.durability), headStats.miningspeed*ConfigHandler.mineSpeedNerf/100, headStats.attack, headStats.harvestLevel);
			} else if (statRegisterEvent.stats instanceof HandleMaterialStats) {
				handleStats = (HandleMaterialStats) oldStats;
				newStats = new HandleMaterialStats(handleStats.modifier, nerfDurability(handleStats.durability));
			} else if (statRegisterEvent.stats instanceof ExtraMaterialStats) {
				extraStats = (ExtraMaterialStats) oldStats;
				newStats = new ExtraMaterialStats(nerfDurability(extraStats.extraDurability));
			}
		}

		if (materialsToTweak.containsKey(statRegisterEvent.material.identifier)) {
			String[] entries = materialsToTweak.get(statRegisterEvent.material.identifier);

			if (statRegisterEvent.stats instanceof HeadMaterialStats) {
				headStats = (HeadMaterialStats) (headStats == null ? oldStats:newStats);
				newStats = new HeadMaterialStats(entries[1].equals("d") ? headStats.durability:Integer.parseInt(entries[1]), entries[2].equals("d") ? headStats.miningspeed:Float.parseFloat(entries[2]), entries[3].equals("d") ? headStats.attack:Float.parseFloat(entries[3]), entries[4].equals("d") ? headStats.harvestLevel:Integer.parseInt(entries[4]));
			} else if (statRegisterEvent.stats instanceof HandleMaterialStats) {
				handleStats = (HandleMaterialStats) (handleStats == null ? oldStats:newStats);
				newStats = new HandleMaterialStats(entries[5].equals("d") ? handleStats.modifier:Float.parseFloat(entries[5]), entries[6].equals("d") ? handleStats.durability:Integer.parseInt(entries[6]));
			} else if (statRegisterEvent.stats instanceof ExtraMaterialStats) {
				extraStats = (ExtraMaterialStats) (extraStats == null ? oldStats:newStats);
				newStats = new ExtraMaterialStats(entries[7].equals("d") ? extraStats.extraDurability:Integer.parseInt(entries[7]));
			} else if (statRegisterEvent.stats instanceof BowMaterialStats) {
				bowStats = (BowMaterialStats) oldStats;
				newStats = new BowMaterialStats(entries[8].equals("d") ? bowStats.drawspeed:Float.parseFloat(entries[8]), entries[9].equals("d") ? bowStats.range:Float.parseFloat(entries[9]), entries[10].equals("d") ? bowStats.bonusDamage:Float.parseFloat(entries[10]));
			}
		}
		if (newStats != null)
			statRegisterEvent.overrideResult(newStats);
	}

	int nerfDurability(int durability) {
		int newDurability = durability * ConfigHandler.durabilityNerf/100;
		if (ConfigHandler.hardcoreNerfs) {
			if (ConfigHandler.durabilityNerf < 100) {
				if (newDurability < 0)
					newDurability += durability;
			} else {
				if (newDurability < 0)
					newDurability -= durability;
			}
		}
		return newDurability;
	}
}
