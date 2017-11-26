package com.rcx.tweaconstruct.tweaks;

import java.lang.reflect.Field;

import com.rcx.tweaconstruct.ConfigHandler;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.materials.Material;

public class PatternCosts {
	public static void preInit() {
		if (ConfigHandler.toolpartCostList.length == 0)
			return;

		for (String entry : ConfigHandler.toolpartCostList) {
			String[] entries = entry.split(":");
			if (entries.length != 3)
				continue;
			Item partItem = Item.REGISTRY.getObject(new ResourceLocation(entries[0], entries[1]));
			if (partItem == null)
				continue;
			try {
				Field f = partItem.getClass().getDeclaredField("cost");
				f.setAccessible(true);
				try {
					f.setInt(partItem, (int) (Material.VALUE_Ingot * Double.parseDouble(entries[2])));
				} catch (IllegalArgumentException e) {
					continue;
				} catch (IllegalAccessException e) {
					continue;
				}
			} catch (NoSuchFieldException e) {
				continue;
			} catch (SecurityException e) {
				continue;
			}
		}
	}
}
