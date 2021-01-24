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

public class TraitTweaks {

	public static List<String> cancelledMaterials = new ArrayList<String>();
	public static Map<String, String[]> materialsToTweak = new HashMap<String, String[]>();
	public static boolean weAreNotDoneYet = true;

	public static void preInit() {
		MinecraftForge.EVENT_BUS.register(new TraitTweaks());

		if (ConfigHandler.traitTweaksList.length != 0)
			for (String entry : ConfigHandler.traitTweaksList) {
				String[] entries = entry.split(":");
				if (entries.length != 3 && entries.length != 4) {
					TweakersConstruct.logger.warn("[Trait Tweaks] Entry: " + entry + " has incorrect syntax, skipping.");
					continue;
				}

				materialsToTweak.put(entries[0] + ":" + entries[1], entries);
				if (entries.length != 4 || Boolean.parseBoolean(entries[2]))
					cancelledMaterials.add(entries[0]);
			}
	}

	@SubscribeEvent
	public void onTraitRegister(MaterialEvent.TraitRegisterEvent<ITrait> traitRegisterEvent) {
		if (weAreNotDoneYet && cancelledMaterials.contains(traitRegisterEvent.material.identifier))
			traitRegisterEvent.setCanceled(true);
		TinkerRegistry.addTrait(traitRegisterEvent.trait);
	}

	public static void init() {
		weAreNotDoneYet = false;

		for (String key : materialsToTweak.keySet()) {
			String[] splitKey = key.split(":");
			Material material = TinkerRegistry.getMaterial(splitKey[0]);

			if (material.equals(Material.UNKNOWN)) {
				TweakersConstruct.logger.warn("[Trait Tweaks] Could not find material: " + splitKey[0] + ", skipping.");
				continue;
			}

			String[] traits = materialsToTweak.get(key)[2].split(",");

			for (String traitId : traits) {
				ITrait trait = TinkerRegistry.getTrait(traitId);

				if (trait == null) {
					TweakersConstruct.logger.warn("[Trait Tweaks] Could not find trait: " + traitId + ", skipping.");
					continue;
				}

				if (splitKey[1].equals("all"))
					material.addTrait(trait);
				else
					material.addTrait(trait, splitKey[1]);
			}
		}
	}
}
