package com.rcx.tweaconstruct.tweaks;

import java.lang.reflect.Field;

import com.rcx.tweaconstruct.ConfigHandler;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.ToolPart;

public class PatternCosts {
	public static void init() {
		if (ConfigHandler.toolpartCostList.length == 0)
			return;

		for (String entry : ConfigHandler.toolpartCostList) {
			String[] entries = entry.split(":");
			if (entries.length != 3)
				continue;
			Item partItem = Item.REGISTRY.getObject(new ResourceLocation(entries[0], entries[1]));
			if (partItem == null || !(partItem instanceof ToolPart))
				continue;
			try {
				Field f = ToolPart.class.getDeclaredField("cost");
				f.setAccessible(true);
				try {
					f.setInt(partItem, (int) (Material.VALUE_Ingot * Double.parseDouble(entries[2])));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					continue;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					continue;
				}
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
				continue;
			} catch (SecurityException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}
