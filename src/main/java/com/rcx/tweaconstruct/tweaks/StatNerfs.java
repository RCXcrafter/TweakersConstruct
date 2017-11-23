package com.rcx.tweaconstruct.tweaks;

import com.rcx.tweaconstruct.ConfigHandler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.Event;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.events.MaterialEvent;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.IMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.MaterialTypes;

public class StatNerfs {
	public static void init() {
		if (ConfigHandler.durabilityNerf == 100 && ConfigHandler.mineSpeedNerf == 100)
			return;

		for (Material material : TinkerRegistry.getAllMaterialsWithStats(MaterialTypes.HEAD)) {
			HeadMaterialStats headStats = material.getStats(MaterialTypes.HEAD);
			HandleMaterialStats handleStats = material.getStats(MaterialTypes.HANDLE);
			ExtraMaterialStats extraStats = material.getStats(MaterialTypes.EXTRA);
			//BowMaterialStats bowStats = material.getStats(MaterialTypes.HEAD);

			int headDurability = headStats.durability * ConfigHandler.durabilityNerf / 100;
			int handleDurability = handleStats.durability * ConfigHandler.durabilityNerf / 100;
			int extraDurability = extraStats.extraDurability * ConfigHandler.durabilityNerf / 100;
			float miningSpeed = headStats.miningspeed * ConfigHandler.mineSpeedNerf / 100;

			if (ConfigHandler.hardcoreNerfs) {
				if (ConfigHandler.durabilityNerf < 100) {
					if (headDurability < 0)
						headDurability += headStats.durability;
					if (handleDurability < 0)
						handleDurability += handleStats.durability;
					if (extraDurability < 0)
						extraDurability += extraStats.extraDurability;
				} else {
					if (headDurability < 0)
						headDurability -= headStats.durability;
					if (handleDurability < 0)
						handleDurability -= handleStats.durability;
					if (extraDurability < 0)
						extraDurability -= extraStats.extraDurability;
				}
			}

			tweakMaterialStats(material, new HeadMaterialStats(headDurability, miningSpeed, headStats.attack, headStats.harvestLevel));
			tweakMaterialStats(material, new HandleMaterialStats(handleStats.modifier, handleDurability));
			tweakMaterialStats(material, new ExtraMaterialStats(extraDurability));
			//tweakMaterialStats(material, new BowMaterialStats(bowStats.drawspeed, bowStats.range, bowStats.bonusDamage));
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

		ModContainer activeMod = Loader.instance().activeModContainer();
		//TinkerRegistry.putStatTrace(material.identifier, stats, activeMod);
	}
}
