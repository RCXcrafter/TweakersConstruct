package com.rcx.tweaconstruct.tweaks;

import java.lang.reflect.Field;

import com.rcx.tweaconstruct.ConfigHandler;
import com.rcx.tweaconstruct.TweakersConstruct;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.IToolPart;

public class ToolpartCosts {
	public static void init() {
		if (ConfigHandler.toolpartCostList.length == 0)
			return;

		for (String entry : ConfigHandler.toolpartCostList) {
			String[] entries = entry.split(":");
			if (entries.length != 3) {
				TweakersConstruct.logger.warn("[Toolpart Cost] Entry: " + entry + " has incorrect syntax, skipping.");
				continue;
			}
			Item partItem = Item.REGISTRY.getObject(new ResourceLocation(entries[0], entries[1]));
			if (partItem == null || !(partItem instanceof IToolPart)) {
				TweakersConstruct.logger.warn("[Toolpart Cost] Could not find tool part: " + entries[0] + entries[1] + ", skipping.");
				continue;
			}
			try {
				Class clazz = partItem.getClass();
				while (true) {
					try {
						clazz.getDeclaredField("cost");
						break;
					} catch (NoSuchFieldException e) {
						clazz = clazz.getSuperclass();
					}
				}
				Field f = clazz.getDeclaredField("cost");
				f.setAccessible(true);
				f.setInt(partItem, (int) (Material.VALUE_Ingot * Double.parseDouble(entries[2])));
			} catch (Exception e) {
				TweakersConstruct.logger.error("[Toolpart Cost] Could not modify cost for entry: " + entry + ", skipping.");
				e.printStackTrace();
				continue;
			}
		}
	}
}
