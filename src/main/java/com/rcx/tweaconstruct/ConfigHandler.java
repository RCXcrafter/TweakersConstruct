package com.rcx.tweaconstruct;

import net.minecraftforge.common.config.Configuration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.BowMaterialStats;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.IMaterialStats;
import slimeknights.tconstruct.library.materials.Material;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.rcx.tweaconstruct.tweaks.StatTweaks;

public class ConfigHandler {

	public static Configuration config;

	// Categories
	public static String stats = "Stat tweaks";
	public static String misc = "Miscelleaneous";

	// Options
	public static Integer durabilityNerf = 75;
	public static Integer mineSpeedNerf = 75;
	public static Boolean hardcoreNerfs = true;
	public static Boolean fillDefaults = false;
	public static Boolean toolVincibility = true;
	public static String[] toolpartCostList;
	private static String[] toolpartCostListDefaults = {
			"tconstruct:pick_head:3",
			"tconstruct:axe_head:3",
			"tconstruct:pan_head:4"
	};
	public static String[] statTweaksList;
	private static String[] statTweaksListDefaults = {
			"stone:90:3.0:3:1:0.5:-87:15:5.0:0.4:-1.0"
	};
	public static String[] RemoveMaterialList;
	private static String[] RemoveMaterialListDefaults = {
			"paper"
	};

	public static void preInit(File file) {
		config = new Configuration(file);
		syncConfig();
	}

	public static void syncConfig() {
		config.setCategoryComment(stats, "Tweaks to material stats");

		durabilityNerf = config.getInt("Durability Nerf", stats, durabilityNerf, 0, 1000, "All durability values will be modified with this percentage."
				+ "\nSet to 100 to disable.");
		mineSpeedNerf = config.getInt("Miningspeed Nerf", stats, mineSpeedNerf, 0, 1000, "All mining speed values will be modified with this percentage."
				+ "\nThese options can also be used to buff stats, but just.... don't.");

		hardcoreNerfs = config.getBoolean("Hardcore Nerfs", stats, hardcoreNerfs, "negative durability values are decreased further instead of increased when durabilitynerf is below 100, does the opposite for values above 100");

		fillDefaults = config.getBoolean("Fill Defaults", stats, fillDefaults, "Set this to true to fill the stat tweaks list below with all the default values for all materials"
				+ "\nThis option disables itself after filling the list and it will also delete any tweaks you already had.");

		statTweaksList = config.getStringList("Stat Tweaks", stats, statTweaksListDefaults,
				"Here you can change the stats of specific materials, this takes priority over the percentage nerfs."
						+ "\nThe syntax is: MaterialID:HeadDurability:MiningSpeed:AttackDamage:HarvestLevel:HandleModifier:HandleDurability:ExtraDurability:DrawSpeed:Range:BonusDamage"
						+ "\nSet any value to d to keep it as the default value.");

		config.setCategoryComment(misc, "Some miscelleaneous but useful tweaks.");

		toolpartCostList = config.getStringList("Toolpart Cost", misc, toolpartCostListDefaults,
				"The syntax is: ModID:ItemID:MaterialCost"
						+ "\nModID: The mod id of the toolpart."
						+ "\nItemID: The id of the toolpart."
						+ "\nMaterialCost: The amount of ingots the toolpart should cost.");

		RemoveMaterialList = config.getStringList("Remove Materials", misc, RemoveMaterialListDefaults, "Here you can remove any material by adding its id to this list");

		toolVincibility = config.getBoolean("Tool Uninvincibility", misc, toolVincibility, "Turning this option on will stop tinkers tools from being invincible (they can despawn and burn in lava)");

		if(config.hasChanged())
			config.save();
	}

	public static void postInit() {
		if (fillDefaults) {
			List<String> defaultStats = new ArrayList<String>();
			for (Material material : TinkerRegistry.getAllMaterials()) {
				String materialStats = material.getIdentifier() + ":";

				HeadMaterialStats headStats = null;
				HandleMaterialStats handleStats = null;
				ExtraMaterialStats extraStats = null;
				BowMaterialStats bowStats = null;
				for (IMaterialStats stat : material.getAllStats()) {
					if (stat instanceof HeadMaterialStats)
						headStats = (HeadMaterialStats) stat;
					else if (stat instanceof HandleMaterialStats)
						handleStats = (HandleMaterialStats) stat;
					else if (stat instanceof ExtraMaterialStats)
						extraStats = (ExtraMaterialStats) stat;
					else if (stat instanceof BowMaterialStats)
						bowStats = (BowMaterialStats) stat;
				}
				if (headStats == null)
					materialStats += "d:d:d:d:";
				else
					materialStats += headStats.durability + ":" + headStats.miningspeed + ":" + headStats.attack + ":" + headStats.harvestLevel + ":";

				if (handleStats == null)
					materialStats += "d:d:";
				else
					materialStats += handleStats.modifier + ":" + handleStats.durability + ":";

				if (extraStats == null)
					materialStats += "d:";
				else
					materialStats += extraStats.extraDurability + ":";

				if (bowStats == null)
					materialStats += "d:d:d";
				else
					materialStats += bowStats.drawspeed + ":" + bowStats.range + ":" + bowStats.bonusDamage;

				if (!materialStats.endsWith(":d:d:d:d:d:d:d:d:d:d"))
					defaultStats.add(materialStats);
			}
			config.get(stats, "Stat Tweaks", statTweaksListDefaults).set(defaultStats.toArray(new String[defaultStats.size()]));
			config.get(stats, "Fill Defaults", fillDefaults).set(false);
			config.get(stats, "Stat Tweaks", statTweaksListDefaults).setComment("Here you can change the stats of specific materials, this takes priority over the percentage nerfs."
					+ "\nThe syntax is: MaterialID:HeadDurability:MiningSpeed:AttackDamage:HarvestLevel:HandleModifier:HandleDurability:ExtraDurability:DrawSpeed:Range:BonusDamage"
					+ "\nSet any value to d to keep it as the default value.");
			config.get(stats, "Fill Defaults", fillDefaults).setComment("Set this to true to fill the stat tweaks list below with all the default values for all materials"
					+ "\nThis option disables itself after filling the list and it will also delete any tweaks you already had.");
			config.save();
		}
	}
}
