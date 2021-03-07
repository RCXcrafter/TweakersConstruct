package com.rcx.tweaconstruct.tweaks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rcx.tweaconstruct.ConfigHandler;
import com.rcx.tweaconstruct.TweakersConstruct;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.events.MaterialEvent;
import slimeknights.tconstruct.library.materials.IMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.ITrait;

public class PartCreationTweaks {

	public static Map<String, String[]> materialsToTweak = new HashMap<String, String[]>();
	public static boolean weAreNotDoneYet = true;

	public static void preInit() {
		MinecraftForge.EVENT_BUS.register(new PartCreationTweaks());

		if (ConfigHandler.PartCreationList.length != 0)
			for (String entry : ConfigHandler.PartCreationList) {
				String[] entries = entry.split(":");
				if (entries.length != 3) {
					TweakersConstruct.logger.warn("[Trait Tweaks] Entry: " + entry + " has incorrect syntax, skipping.");
					continue;
				}

				materialsToTweak.put(entries[0], entries);
			}
	}

	@SubscribeEvent
	public void onTraitRegister(MaterialEvent.MaterialRegisterEvent materialRegisterEvent) {
		if (materialsToTweak.containsKey(materialRegisterEvent.material.identifier)) {
			String[] entries = materialsToTweak.get(materialRegisterEvent.material.identifier);
			materialRegisterEvent.material.setCraftable(Boolean.parseBoolean(entries[1]));
			materialRegisterEvent.material.setCastable(Boolean.parseBoolean(entries[2]));
		}
	}
}
