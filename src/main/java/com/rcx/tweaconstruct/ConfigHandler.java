package com.rcx.tweaconstruct;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.ArrowShaftMaterialStats;
import slimeknights.tconstruct.library.materials.BowMaterialStats;
import slimeknights.tconstruct.library.materials.BowStringMaterialStats;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.FletchingMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.IMaterialStats;
import slimeknights.tconstruct.library.materials.Material;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.rcx.tweaconstruct.tweaks.StatTweaks;

import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import lance5057.tDefense.core.materials.stats.ArmorMaterialStats;
import lance5057.tDefense.core.materials.stats.ChestMaterialStats;
import lance5057.tDefense.core.materials.stats.FeetMaterialStats;
import lance5057.tDefense.core.materials.stats.HelmMaterialStats;
import lance5057.tDefense.core.materials.stats.LegsMaterialStats;
import lance5057.tDefense.core.materials.stats.ShieldMaterialStats;

public class ConfigHandler {

	public static Configuration config;

	// Categories
	public static String stats = "Stat tweaks";
	public static String traits = "Trait tweaks";
	public static String misc = "Miscelleaneous";

	// Options
	public static Integer durabilityNerf = 75;
	public static Integer mineSpeedNerf = 75;
	public static Integer attackNerf = 75;
	public static Integer armorNerf = 75;
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
	public static String[] arrowShaftStatTweaksList;
	private static String[] arrowShaftStatTweaksListDefaults = {
			"wood:1.0:0"
	};
	public static String[] bowStringStatTweaksList;
	private static String[] bowStringStatTweaksListDefaults = {
			"string:1.0"
	};
	public static String[] fletchingStatTweaksList;
	private static String[] fletchingStatTweaksListDefaults = {
			"feather:1.0:1.0"
	};
	public static String[] armoryStatTweaksList;
	private static String[] armoryStatTweaksListDefaults = {
			"iron:12.0:15.0:0.85:5.0:0.0:3.5"
	};
	public static String[] armorStatTweaksList;
	private static String[] armorStatTweaksListDefaults = {
			"iron:204:2:0:-4.0:204:6:0:-5.0:204:5:0:-4.0:204:2:0:-3.0"
	};
	public static String[] shieldStatTweaksList;
	private static String[] shieldStatTweaksListDefaults = {
			"wood:35:25"
	};
	public static String[] traitTweaksList;
	private static String[] traitTweaksListDefaults = {
			"paper:all:tasty",
			"paper:head:cheap",
			"paper:handle:autosmelt",
			"paper:extra:holy,hellish",
			"paper:bow:coldblooded",
			"paper:bowstring:crude",
			"paper:projectile:dense",
			"paper:shaft:heavy",
			"paper:fletching:alien"
	};
	public static String[] RemoveMaterialList;
	private static String[] RemoveMaterialListDefaults = {
			"paper"
	};
	public static String[] RemoveModifierList;
	private static String[] RemoveModifierListDefaults = {
			"mending_moss"
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
		attackNerf = config.getInt("Attackdamage Nerf", stats, attackNerf, 0, 1000, "All attack damage values will be modified with this percentage."
				+ "\nSet to 100 to disable.");
		if (Loader.isModLoaded("conarm") || Loader.isModLoaded("tinkerscompendium")) {
			armorNerf = config.getInt("Armor Nerf", stats, armorNerf, 0, 1000, "All defense and toughness values will be modified with this percentage."
					+ "\nSet to 100 to disable.");
		}

		hardcoreNerfs = config.getBoolean("Hardcore Nerfs", stats, hardcoreNerfs, "negative durability values are decreased further instead of increased when durabilitynerf is below 100, does the opposite for values above 100");

		fillDefaults = config.getBoolean("Fill Defaults", stats, fillDefaults, "Set this to true to fill the stat tweaks list below with all the default values for all materials"
				+ "\nThis option disables itself after filling the list and it will also delete any tweaks you already had.");

		statTweaksList = config.getStringList("Stat Tweaks", stats, statTweaksListDefaults,
				"Here you can change the stats of specific materials, this takes priority over the percentage nerfs."
						+ "\nThe syntax is: MaterialID:HeadDurability:MiningSpeed:AttackDamage:HarvestLevel:HandleModifier:HandleDurability:ExtraDurability:DrawSpeed:Range:BonusDamage"
						+ "\nSet any value to d to keep it as the default value.");

		arrowShaftStatTweaksList = config.getStringList("Arrow Shaft Stat Tweaks", stats, arrowShaftStatTweaksListDefaults,
				"Here you can change the stats of specific materials, this takes priority over the percentage nerfs."
						+ "\nThe syntax is: MaterialID:Modifier:BonusAmmo"
						+ "\nSet any value to d to keep it as the default value.");

		bowStringStatTweaksList = config.getStringList("Bowstring Stat Tweaks", stats, bowStringStatTweaksListDefaults,
				"Here you can change the stats of specific materials, this takes priority over the percentage nerfs."
						+ "\nThe syntax is: MaterialID:Modifier"
						+ "\nSet any value to d to keep it as the default value.");

		fletchingStatTweaksList = config.getStringList("Fletching Stat Tweaks", stats, fletchingStatTweaksListDefaults,
				"Here you can change the stats of specific materials, this takes priority over the percentage nerfs."
						+ "\nThe syntax is: MaterialID:Accuracy:Modifier"
						+ "\nSet any value to d to keep it as the default value.");

		if (Loader.isModLoaded("conarm")) {
			armoryStatTweaksList = config.getStringList("Armory Stat Tweaks", stats, armoryStatTweaksListDefaults,
					"Here you can change the stats of specific materials, this takes priority over the percentage nerfs."
							+ "\nThe syntax is: MaterialID:CoreDurability:Defense:Modifier:PlatesDurability:Toughness:ExtraDurability"
							+ "\nSet any value to d to keep it as the default value.");
		}

		if (Loader.isModLoaded("tinkerscompendium")) {
			armorStatTweaksList = config.getStringList("Armor Stat Tweaks", stats, armorStatTweaksListDefaults,
					"Here you can change the stats of specific materials, this takes priority over the percentage nerfs."
							+ "\nThe syntax is: MaterialID:HelmDurability:HelmRating:HelmToughness:HelmPotency:ChestDurability:ChestRating:ChestToughness:ChestPotency:LegsDurability:LegsRating:LegsToughness:LegsPotency:BootsDurability:BootsRating:BootsToughness:BootsPotency"
							+ "\nSet any value to d to keep it as the default value.");

			shieldStatTweaksList = config.getStringList("Shield Stat Tweaks", stats, shieldStatTweaksListDefaults,
					"Here you can change the stats of specific materials, this takes priority over the percentage nerfs."
							+ "\nThe syntax is: MaterialID:Durability:PercentBlocked"
							+ "\nSet any value to d to keep it as the default value.");
		}

		config.setCategoryComment(traits, "Tweaks related to material traits.");

		traitTweaksList = config.getStringList("Trait tweaks", traits, traitTweaksListDefaults, 
				"Here you can change the traits of materials for certain parts or all parts."
						+ "\nThe syntax is: MaterialID:Parts:Trait1,Trait2,etc.");

		config.setCategoryComment(misc, "Some miscelleaneous but useful tweaks.");

		toolpartCostList = config.getStringList("Toolpart Cost", misc, toolpartCostListDefaults,
				"The syntax is: ModID:ItemID:MaterialCost"
						+ "\nModID: The mod id of the toolpart."
						+ "\nItemID: The id of the toolpart."
						+ "\nMaterialCost: The amount of ingots the toolpart should cost.");

		RemoveMaterialList = config.getStringList("Remove Materials", misc, RemoveMaterialListDefaults, "Here you can remove any material by adding its id to this list");

		RemoveModifierList = config.getStringList("Remove Modifiers", misc, RemoveModifierListDefaults, "Here you can remove any modifier by adding its id to this list");

		toolVincibility = config.getBoolean("Tool Uninvincibility", misc, toolVincibility, "Turning this option on will stop tinkers tools from being invincible (they can despawn and burn in lava)");

		if(config.hasChanged())
			config.save();
	}

	public static void postInit() {
		if (fillDefaults) {
			List<String> defaultStats = new ArrayList<String>();
			List<String> defaultShaftStats = new ArrayList<String>();
			List<String> defaultStringStats = new ArrayList<String>();
			List<String> defaultFletchingStats = new ArrayList<String>();
			List<String> defaultArmoryStats = new ArrayList<String>();
			List<String> defaultArmorStats = new ArrayList<String>();
			List<String> defaultShieldStats = new ArrayList<String>();
			for (Material material : TinkerRegistry.getAllMaterials()) {
				String materialStats = material.getIdentifier() + ":";
				String materialShaftStats = material.getIdentifier() + ":";
				String materialStringStats = material.getIdentifier() + ":";
				String materialFletchingStats = material.getIdentifier() + ":";
				String materialArmoryStats = material.getIdentifier() + ":";
				String materialArmorStats = material.getIdentifier() + ":";
				String materialShieldStats = material.getIdentifier() + ":";

				HeadMaterialStats headStats = null;
				HandleMaterialStats handleStats = null;
				ExtraMaterialStats extraStats = null;
				BowMaterialStats bowStats = null;

				ArrowShaftMaterialStats shaftStats = null;
				BowStringMaterialStats stringStats = null;
				FletchingMaterialStats fletchingStats = null;

				CoreMaterialStats coreStats = null;
				PlatesMaterialStats platesStats = null;
				TrimMaterialStats trimStats = null;

				HelmMaterialStats helmStats = null;
				ChestMaterialStats chestStats = null;
				LegsMaterialStats legsStats = null;
				FeetMaterialStats feetStats = null;
				ShieldMaterialStats shieldStats = null;
				for (IMaterialStats stat : material.getAllStats()) {
					if (stat instanceof HeadMaterialStats)
						headStats = (HeadMaterialStats) stat;
					else if (stat instanceof HandleMaterialStats)
						handleStats = (HandleMaterialStats) stat;
					else if (stat instanceof ExtraMaterialStats)
						extraStats = (ExtraMaterialStats) stat;
					else if (stat instanceof BowMaterialStats)
						bowStats = (BowMaterialStats) stat;
					else if (stat instanceof ArrowShaftMaterialStats)
						shaftStats = (ArrowShaftMaterialStats) stat;
					else if (stat instanceof BowStringMaterialStats)
						stringStats = (BowStringMaterialStats) stat;
					else if (stat instanceof FletchingMaterialStats)
						fletchingStats = (FletchingMaterialStats) stat;
					else if (Loader.isModLoaded("conarm") && stat instanceof CoreMaterialStats)
						coreStats = (CoreMaterialStats) stat;
					else if (Loader.isModLoaded("conarm") && stat instanceof PlatesMaterialStats)
						platesStats = (PlatesMaterialStats) stat;
					else if (Loader.isModLoaded("conarm") && stat instanceof TrimMaterialStats)
						trimStats = (TrimMaterialStats) stat;
					else if (Loader.isModLoaded("tinkerscompendium") && stat instanceof HelmMaterialStats)
						helmStats = (HelmMaterialStats) stat;
					else if (Loader.isModLoaded("tinkerscompendium") && stat instanceof ChestMaterialStats)
						chestStats = (ChestMaterialStats) stat;
					else if (Loader.isModLoaded("tinkerscompendium") && stat instanceof LegsMaterialStats)
						legsStats = (LegsMaterialStats) stat;
					else if (Loader.isModLoaded("tinkerscompendium") && stat instanceof FeetMaterialStats)
						feetStats = (FeetMaterialStats) stat;
					else if (Loader.isModLoaded("tinkerscompendium") && stat instanceof ShieldMaterialStats)
						shieldStats = (ShieldMaterialStats) stat;
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

				if (shaftStats == null)
					materialShaftStats += "d:d";
				else
					materialShaftStats += shaftStats.modifier + ":" + shaftStats.bonusAmmo;

				if (!materialShaftStats.endsWith(":d:d"))
					defaultShaftStats.add(materialShaftStats);

				if (stringStats == null)
					materialStringStats += "d";
				else
					materialStringStats += stringStats.modifier;

				if (!materialStringStats.endsWith(":d"))
					defaultStringStats.add(materialStringStats);

				if (fletchingStats == null)
					materialFletchingStats += "d:d";
				else
					materialFletchingStats += fletchingStats.accuracy + ":" + fletchingStats.modifier;

				if (!materialFletchingStats.endsWith(":d:d"))
					defaultFletchingStats.add(materialFletchingStats);

				if (Loader.isModLoaded("conarm")) {
					if (coreStats == null)
						materialArmoryStats += "d:d:";
					else
						materialArmoryStats += coreStats.durability + ":" + coreStats.defense + ":";

					if (platesStats == null)
						materialArmoryStats += "d:d:d:";
					else
						materialArmoryStats += platesStats.modifier + ":" + platesStats.durability + ":" + platesStats.toughness + ":";

					if (trimStats == null)
						materialArmoryStats += "d";
					else
						materialArmoryStats += trimStats.extraDurability;

					if (!materialArmoryStats.endsWith(":d:d:d:d:d:d"))
						defaultArmoryStats.add(materialArmoryStats);
				}

				if (Loader.isModLoaded("tinkerscompendium")) {
					if (helmStats == null)
						materialArmorStats += "d:d:d:d:";
					else
						materialArmorStats += helmStats.durability + ":" + helmStats.rating + ":" + helmStats.toughness + ":" + helmStats.potency + ":";

					if (chestStats == null)
						materialArmorStats += "d:d:d:d:";
					else
						materialArmorStats += chestStats.durability + ":" + chestStats.rating + ":" + chestStats.toughness + ":" + chestStats.potency + ":";

					if (legsStats == null)
						materialArmorStats += "d:d:d:d:";
					else
						materialArmorStats += legsStats.durability + ":" + legsStats.rating + ":" + legsStats.toughness + ":" + legsStats.potency + ":";

					if (feetStats == null)
						materialArmorStats += "d:d:d:d";
					else
						materialArmorStats += feetStats.durability + ":" + feetStats.rating + ":" + feetStats.toughness + ":" + feetStats.potency;

					if (!materialArmorStats.endsWith(":d:d:d:d:d:d:d:d:d:d:d:d:d:d:d:d"))
						defaultArmorStats.add(materialArmorStats);

					if (shieldStats == null)
						materialShieldStats += "d:d";
					else
						materialShieldStats += shieldStats.durability + ":" + shieldStats.percentBlocked;

					if (!materialShieldStats.endsWith(":d:d"))
						defaultShieldStats.add(materialShieldStats);
				}
			}
			config.get(stats, "Stat Tweaks", statTweaksListDefaults).set(defaultStats.toArray(new String[defaultStats.size()]));
			config.get(stats, "Arrow Shaft Stat Tweaks", arrowShaftStatTweaksListDefaults).set(defaultShaftStats.toArray(new String[defaultShaftStats.size()]));
			config.get(stats, "Bowstring Stat Tweaks", bowStringStatTweaksListDefaults).set(defaultStringStats.toArray(new String[defaultStringStats.size()]));
			config.get(stats, "Fletching Stat Tweaks", fletchingStatTweaksListDefaults).set(defaultFletchingStats.toArray(new String[defaultFletchingStats.size()]));
			if (Loader.isModLoaded("conarm")) {
				config.get(stats, "Armory Stat Tweaks", armoryStatTweaksListDefaults).set(defaultArmoryStats.toArray(new String[defaultArmoryStats.size()]));
			}
			if (Loader.isModLoaded("tinkerscompendium")) {
				config.get(stats, "Armor Stat Tweaks", armorStatTweaksListDefaults).set(defaultArmorStats.toArray(new String[defaultArmorStats.size()]));
				config.get(stats, "Shield Stat Tweaks", shieldStatTweaksListDefaults).set(defaultShieldStats.toArray(new String[defaultShieldStats.size()]));
			}
			config.get(stats, "Fill Defaults", fillDefaults).set(false);
			config.get(stats, "Stat Tweaks", statTweaksListDefaults).setComment("Here you can change the stats of specific materials, this takes priority over the percentage nerfs."
					+ "\nThe syntax is: MaterialID:HeadDurability:MiningSpeed:AttackDamage:HarvestLevel:HandleModifier:HandleDurability:ExtraDurability:DrawSpeed:Range:BonusDamage"
					+ "\nSet any value to d to keep it as the default value.");
			config.get(stats, "Arrow Shaft Stat Tweaks", arrowShaftStatTweaksListDefaults).setComment("Here you can change the stats of specific materials, this takes priority over the percentage nerfs."
					+ "\nThe syntax is: MaterialID:Modifier:BonusAmmo"
					+ "\nSet any value to d to keep it as the default value.");
			config.get(stats, "Bowstring Stat Tweaks", bowStringStatTweaksListDefaults).setComment("Here you can change the stats of specific materials, this takes priority over the percentage nerfs."
					+ "\nThe syntax is: MaterialID:Modifier"
					+ "\nSet any value to d to keep it as the default value.");
			config.get(stats, "Fletching Stat Tweaks", fletchingStatTweaksListDefaults).setComment("Here you can change the stats of specific materials, this takes priority over the percentage nerfs."
					+ "\nThe syntax is: MaterialID:Accuracy:Modifier"
					+ "\nSet any value to d to keep it as the default value.");
			if (Loader.isModLoaded("conarm")) {
				config.get(stats, "Armory Stat Tweaks", armoryStatTweaksListDefaults).setComment("Here you can change the stats of specific materials, this takes priority over the percentage nerfs."
						+ "\nThe syntax is: MaterialID:CoreDurability:Defense:Modifier:PlatesDurability:Toughness:ExtraDurability"
						+ "\nSet any value to d to keep it as the default value.");
			}
			if (Loader.isModLoaded("tinkerscompendium")) {
				config.get(stats, "Armor Stat Tweaks", armorStatTweaksListDefaults).setComment("Here you can change the stats of specific materials, this takes priority over the percentage nerfs."
						+ "\nThe syntax is: MaterialID:HelmDurability:HelmRating:HelmToughness:HelmPotency:ChestDurability:ChestRating:ChestToughness:ChestPotency:LegsDurability:LegsRating:LegsToughness:LegsPotency:BootsDurability:BootsRating:BootsToughness:BootsPotency"
						+ "\nSet any value to d to keep it as the default value.");
				config.get(stats, "Shield Stat Tweaks", shieldStatTweaksListDefaults).setComment("Here you can change the stats of specific materials, this takes priority over the percentage nerfs."
						+ "\nThe syntax is: MaterialID:Durability:PercentBlocked"
						+ "\nSet any value to d to keep it as the default value.");
			}
			config.get(stats, "Fill Defaults", fillDefaults).setComment("Set this to true to fill the stat tweaks list below with all the default values for all materials"
					+ "\nThis option disables itself after filling the list and it will also delete any tweaks you already had.");
			config.save();
		}
	}
}
