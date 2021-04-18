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
import slimeknights.tconstruct.library.events.MaterialEvent.IntegrationEvent;
import slimeknights.tconstruct.library.events.MaterialEvent.MaterialRegisterEvent;
import slimeknights.tconstruct.library.events.TinkerRegisterEvent.TableCastingRegisterEvent;
import slimeknights.tconstruct.library.materials.IMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.library.tinkering.MaterialItem;
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
					TweakersConstruct.logger.warn("[Part Creation Tweaks] Entry: " + entry + " has incorrect syntax, skipping.");
					continue;
				}

				materialsToTweak.put(entries[0], entries);
			}
	}

	@SubscribeEvent
	public void onMaterialRegister(MaterialRegisterEvent event) {
		if (event.material != null && materialsToTweak.containsKey(event.material.identifier)) {
			String[] entries = materialsToTweak.get(event.material.identifier);
			event.material.setCraftable(Boolean.parseBoolean(entries[1]));
			event.material.setCastable(Boolean.parseBoolean(entries[2]));
		}
	}

	@SubscribeEvent
	public void onMaterialIntegrated(IntegrationEvent event) {
		if (event.material != null && materialsToTweak.containsKey(event.material.identifier)) {
			String[] entries = materialsToTweak.get(event.material.identifier);
			boolean formerlyCastable = event.material.isCastable();
			event.material.setCraftable(Boolean.parseBoolean(entries[1]));
			event.material.setCastable(Boolean.parseBoolean(entries[2]));
			if (formerlyCastable && !Boolean.parseBoolean(entries[2])) {
				event.materialIntegration.material = null;
				event.material.setRepresentativeItem(event.materialIntegration.representativeItem);
				event.setCanceled(true);
			}
		}
	}

	/*@SubscribeEvent
	public void onCastingRegistered(TableCastingRegisterEvent event) {
		if (event.getRecipe() instanceof CastingRecipe) {
			CastingRecipe recipe = (CastingRecipe) event.getRecipe();
			if (recipe.getResult().getItem() instanceof MaterialItem) {
				String materialID = ((MaterialItem) recipe.getResult().getItem()).getMaterialID(recipe.getResult());
				if (materialsToTweak.containsKey(materialID) && !Boolean.parseBoolean(materialsToTweak.get(materialID)[2])) {
					event.setCanceled(true);
				}
			}
		}
	}*/

	public static void postInit() {
		for (String entry : materialsToTweak.keySet().toArray(new String[materialsToTweak.size()])) {
			String[] entries = materialsToTweak.get(entry);
			Material material = TinkerRegistry.getMaterial(entry);
			material.setCraftable(Boolean.parseBoolean(entries[1]));
			material.setCastable(Boolean.parseBoolean(entries[2]));
		}
	}
}
