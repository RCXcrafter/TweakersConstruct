package com.rcx.tweaconstruct.tweaks;

import java.lang.reflect.Field;

import com.rcx.tweaconstruct.ConfigHandler;
import com.rcx.tweaconstruct.TweakersConstruct;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.IToolPart;

public class MaterialAdditions {
	public static void init() {
		if (ConfigHandler.MateriaAdditionsList.length == 0)
			return;

		for (String entry : ConfigHandler.MateriaAdditionsList) {
			String[] entries = entry.split(":");
			if (entries.length != 5 && entries.length != 6) {
				TweakersConstruct.logger.warn("[Material Additions] Entry: " + entry + " has incorrect syntax, skipping.");
				continue;
			}
			if (entries.length == 5) {
				TinkerRegistry.getMaterial(entries[0]).addItem(Item.REGISTRY.getObject(new ResourceLocation(entries[3], entries[4])), Integer.parseInt(entries[1]), Integer.parseInt(entries[2]));
			} else {
				TinkerRegistry.getMaterial(entries[0]).addItem(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(entries[3], entries[4])), 1, Integer.parseInt(entries[5])), Integer.parseInt(entries[1]), Integer.parseInt(entries[2]));
			}
		}
	}
}
