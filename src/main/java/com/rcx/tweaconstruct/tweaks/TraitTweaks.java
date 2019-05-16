package com.rcx.tweaconstruct.tweaks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rcx.tweaconstruct.ConfigHandler;

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
				if (entries.length != 3)
					continue;

				materialsToTweak.put(entries[0] + ":" + entries[1], entries);
				cancelledMaterials.add(entries[0]);
			}
	}

	@SubscribeEvent
	public void onTraitRegister(MaterialEvent.TraitRegisterEvent<ITrait> traitRegisterEvent) {
		if (weAreNotDoneYet && cancelledMaterials.contains(traitRegisterEvent.material.identifier))
			traitRegisterEvent.setCanceled(true);
	}

	public static void postInit() {
		weAreNotDoneYet = false;

		for (String key : materialsToTweak.keySet()) {
			Material material = TinkerRegistry.getMaterial(key.split(":")[0]);

			if (material == null)
				continue;

			String[] traits = materialsToTweak.get(key)[2].split(",");

			for (String traitId : traits) {
				ITrait trait = TinkerRegistry.getTrait(traitId);

				if (trait == null)
					continue;

				if (materialsToTweak.get(key)[1].equals("all"))
					material.addTrait(trait);
				else
					material.addTrait(trait, materialsToTweak.get(key)[1]);
			}
		}
	}
}