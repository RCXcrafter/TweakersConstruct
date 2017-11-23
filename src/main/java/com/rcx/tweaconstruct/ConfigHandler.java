package com.rcx.tweaconstruct;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {

	public static Configuration config;

	// Categories
	public static String misc = "Miscelleaneous";

	// Options
	public static Integer durabilityNerf = 75;
	public static Integer mineSpeedNerf = 75;
	public static Boolean hardcoreNerfs = true;
	public static String[] toolpartCostList;
	private static String[] toolpartCostListDefaults = {
			"tconstruct:pick_head:3",
			"tconstruct:axe_head:3",
			"tconstruct:pan_head:4"
	};

	public static void preInit(File file) {
		config = new Configuration(file);
		syncConfig();
	}

	public static void syncConfig() {
		config.setCategoryComment(misc, "Some miscelleaneous but useful tweaks.");

		toolpartCostList = config.getStringList("Toolpart Cost", misc, toolpartCostListDefaults,
				"The syntax is: ModID:ItemID:MaterialCost"
						+ "\nModID: The mod id of the toolpart."
						+ "\nItemID: The id of the toolpart."
						+ "\nMaterialCost: The amount of ingots the toolpart should cost.");

		durabilityNerf = config.getInt("durabilityNerf", misc, durabilityNerf, 0, 1000, "All durability values will be modified with this percentage."
				+ "\nSet to 100 to disable.");
		mineSpeedNerf = config.getInt("mineSpeedNerf", misc, mineSpeedNerf, 0, 1000, "All mining speed values will be modified with this percentage."
				+ "\nThese options can also be used to buff stats, but just.... don't.");

		hardcoreNerfs = config.getBoolean("hardcoreNerfs", misc, hardcoreNerfs, "negative durability values are decreased further instead of increased when durabilitynerf is below 100, does the opposite for values above 100");

		if(config.hasChanged())
			config.save();
	}
}
