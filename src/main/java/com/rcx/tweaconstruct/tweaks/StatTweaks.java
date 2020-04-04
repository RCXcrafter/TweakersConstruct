package com.rcx.tweaconstruct.tweaks;

import java.util.HashMap;
import java.util.Map;

import com.rcx.tweaconstruct.ConfigHandler;
import com.rcx.tweaconstruct.TweakersConstruct;

import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import lance5057.tDefense.core.materials.stats.ArmorMaterialStats;
import lance5057.tDefense.core.materials.stats.ChestMaterialStats;
import lance5057.tDefense.core.materials.stats.FeetMaterialStats;
import lance5057.tDefense.core.materials.stats.HelmMaterialStats;
import lance5057.tDefense.core.materials.stats.LegsMaterialStats;
import lance5057.tDefense.core.materials.stats.ShieldMaterialStats;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.MaterialEvent;
import slimeknights.tconstruct.library.materials.ArrowShaftMaterialStats;
import slimeknights.tconstruct.library.materials.BowMaterialStats;
import slimeknights.tconstruct.library.materials.BowStringMaterialStats;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.FletchingMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.IMaterialStats;

public class StatTweaks {

	public static Map<String, String[]> materialsToTweak = new HashMap<String, String[]>();
	public static Map<String, String[]> shaftMaterialsToTweak = new HashMap<String, String[]>();
	public static Map<String, String[]> stringMaterialsToTweak = new HashMap<String, String[]>();
	public static Map<String, String[]> fletchingMaterialsToTweak = new HashMap<String, String[]>();
	public static Map<String, String[]> armoryMaterialsToTweak = new HashMap<String, String[]>();
	public static Map<String, String[]> armorMaterialsToTweak = new HashMap<String, String[]>();
	public static Map<String, String[]> shieldMaterialsToTweak = new HashMap<String, String[]>();

	public static void preInit() {
		MinecraftForge.EVENT_BUS.register(new StatTweaks());

		if (ConfigHandler.statTweaksList.length != 0)
			for (String entry : ConfigHandler.statTweaksList) {
				String[] entries = entry.split(":");
				if (entries.length != 11) {
					TweakersConstruct.logger.warn("[Stat Tweaks] Entry: " + entry + " has incorrect syntax, skipping.");
					continue;
				}

				materialsToTweak.put(entries[0], entries);
			}

		if (ConfigHandler.arrowShaftStatTweaksList.length != 0)
			for (String entry : ConfigHandler.arrowShaftStatTweaksList) {
				String[] entries = entry.split(":");
				if (entries.length != 3) {
					TweakersConstruct.logger.warn("[Stat Tweaks] Entry: " + entry + " has incorrect syntax, skipping.");
					continue;
				}

				shaftMaterialsToTweak.put(entries[0], entries);
			}

		if (ConfigHandler.bowStringStatTweaksList.length != 0)
			for (String entry : ConfigHandler.bowStringStatTweaksList) {
				String[] entries = entry.split(":");
				if (entries.length != 2) {
					TweakersConstruct.logger.warn("[Stat Tweaks] Entry: " + entry + " has incorrect syntax, skipping.");
					continue;
				}

				stringMaterialsToTweak.put(entries[0], entries);
			}

		if (ConfigHandler.fletchingStatTweaksList.length != 0)
			for (String entry : ConfigHandler.fletchingStatTweaksList) {
				String[] entries = entry.split(":");
				if (entries.length != 3) {
					TweakersConstruct.logger.warn("[Stat Tweaks] Entry: " + entry + " has incorrect syntax, skipping.");
					continue;
				}

				fletchingMaterialsToTweak.put(entries[0], entries);
			}

		if (Loader.isModLoaded("conarm") && ConfigHandler.armoryStatTweaksList.length != 0)
			for (String entry : ConfigHandler.armoryStatTweaksList) {
				String[] entries = entry.split(":");
				if (entries.length != 7) {
					TweakersConstruct.logger.warn("[Stat Tweaks] Entry: " + entry + " has incorrect syntax, skipping.");
					continue;
				}

				armoryMaterialsToTweak.put(entries[0], entries);
			}

		if (Loader.isModLoaded("tinkerscompendium")) {
			if (ConfigHandler.armorStatTweaksList.length != 0)
				for (String entry : ConfigHandler.armorStatTweaksList) {
					String[] entries = entry.split(":");
					if (entries.length != 17) {
						TweakersConstruct.logger.warn("[Stat Tweaks] Entry: " + entry + " has incorrect syntax, skipping.");
						continue;
					}

					armorMaterialsToTweak.put(entries[0], entries);
				}

			if (ConfigHandler.shieldStatTweaksList.length != 0)
				for (String entry : ConfigHandler.shieldStatTweaksList) {
					String[] entries = entry.split(":");
					if (entries.length != 3) {
						TweakersConstruct.logger.warn("[Stat Tweaks] Entry: " + entry + " has incorrect syntax, skipping.");
						continue;
					}

					shieldMaterialsToTweak.put(entries[0], entries);
				}
		}
	}

	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void onStatRegister(MaterialEvent.StatRegisterEvent<IMaterialStats> statRegisterEvent) {
		IMaterialStats newStats = null;

		IMaterialStats oldStats = statRegisterEvent.newStats != null ? statRegisterEvent.newStats:statRegisterEvent.stats;
		HeadMaterialStats headStats = null;
		HandleMaterialStats handleStats = null;
		ExtraMaterialStats extraStats = null;
		BowMaterialStats bowStats = null;

		ArrowShaftMaterialStats shaftStats = null;
		BowStringMaterialStats stringStats = null;
		FletchingMaterialStats fletchingStats = null;

		if (ConfigHandler.durabilityNerf != 100 || ConfigHandler.mineSpeedNerf != 100 || ConfigHandler.attackNerf != 100) {
			if (statRegisterEvent.stats instanceof HeadMaterialStats) {
				headStats = (HeadMaterialStats) oldStats;
				newStats = new HeadMaterialStats(nerfInteger(headStats.durability, ConfigHandler.durabilityNerf), nerfFloat(headStats.miningspeed, ConfigHandler.mineSpeedNerf), nerfFloat(headStats.attack, ConfigHandler.attackNerf), headStats.harvestLevel);
			} else if (statRegisterEvent.stats instanceof HandleMaterialStats) {
				handleStats = (HandleMaterialStats) oldStats;
				newStats = new HandleMaterialStats(handleStats.modifier, nerfInteger(handleStats.durability, ConfigHandler.durabilityNerf));
			} else if (statRegisterEvent.stats instanceof ExtraMaterialStats) {
				extraStats = (ExtraMaterialStats) oldStats;
				newStats = new ExtraMaterialStats(nerfInteger(extraStats.extraDurability, ConfigHandler.durabilityNerf));
			}
		}

		if (materialsToTweak.containsKey(statRegisterEvent.material.identifier)) {
			String[] entries = materialsToTweak.get(statRegisterEvent.material.identifier);

			if (statRegisterEvent.stats instanceof HeadMaterialStats) {
				headStats = (HeadMaterialStats) (headStats == null ? oldStats:newStats);
				newStats = new HeadMaterialStats(entries[1].equals("d") ? headStats.durability:Integer.parseInt(entries[1]), entries[2].equals("d") ? headStats.miningspeed:Float.parseFloat(entries[2]), entries[3].equals("d") ? headStats.attack:Float.parseFloat(entries[3]), entries[4].equals("d") ? headStats.harvestLevel:Integer.parseInt(entries[4]));
			} else if (statRegisterEvent.stats instanceof HandleMaterialStats) {
				handleStats = (HandleMaterialStats) (handleStats == null ? oldStats:newStats);
				newStats = new HandleMaterialStats(entries[5].equals("d") ? handleStats.modifier:Float.parseFloat(entries[5]), entries[6].equals("d") ? handleStats.durability:Integer.parseInt(entries[6]));
			} else if (statRegisterEvent.stats instanceof ExtraMaterialStats) {
				extraStats = (ExtraMaterialStats) (extraStats == null ? oldStats:newStats);
				newStats = new ExtraMaterialStats(entries[7].equals("d") ? extraStats.extraDurability:Integer.parseInt(entries[7]));
			} else if (statRegisterEvent.stats instanceof BowMaterialStats) {
				bowStats = (BowMaterialStats) oldStats;
				newStats = new BowMaterialStats(entries[8].equals("d") ? bowStats.drawspeed:Float.parseFloat(entries[8]), entries[9].equals("d") ? bowStats.range:Float.parseFloat(entries[9]), entries[10].equals("d") ? bowStats.bonusDamage:Float.parseFloat(entries[10]));
			}
		}

		if (shaftMaterialsToTweak.containsKey(statRegisterEvent.material.identifier)) {
			String[] entries = shaftMaterialsToTweak.get(statRegisterEvent.material.identifier);

			if (statRegisterEvent.stats instanceof ArrowShaftMaterialStats) {
				shaftStats = (ArrowShaftMaterialStats) (shaftStats == null ? oldStats:newStats);
				newStats = new ArrowShaftMaterialStats(entries[1].equals("d") ? shaftStats.modifier:Float.parseFloat(entries[1]), entries[2].equals("d") ? shaftStats.bonusAmmo:Integer.parseInt(entries[2]));
			}
		}

		if (stringMaterialsToTweak.containsKey(statRegisterEvent.material.identifier)) {
			String[] entries = stringMaterialsToTweak.get(statRegisterEvent.material.identifier);

			if (statRegisterEvent.stats instanceof BowStringMaterialStats) {
				stringStats = (BowStringMaterialStats) (stringStats == null ? oldStats:newStats);
				newStats = new BowStringMaterialStats(entries[1].equals("d") ? stringStats.modifier:Float.parseFloat(entries[1]));
			}
		}

		if (fletchingMaterialsToTweak.containsKey(statRegisterEvent.material.identifier)) {
			String[] entries = fletchingMaterialsToTweak.get(statRegisterEvent.material.identifier);

			if (statRegisterEvent.stats instanceof FletchingMaterialStats) {
				fletchingStats = (FletchingMaterialStats) (fletchingStats == null ? oldStats:newStats);
				newStats = new FletchingMaterialStats(entries[1].equals("d") ? fletchingStats.accuracy:Float.parseFloat(entries[1]), entries[2].equals("d") ? fletchingStats.modifier:Float.parseFloat(entries[2]));
			}
		}

		if (Loader.isModLoaded("conarm")) {
			CoreMaterialStats coreStats = null;
			PlatesMaterialStats platesStats = null;
			TrimMaterialStats trimStats = null;

			if (ConfigHandler.durabilityNerf != 100 || ConfigHandler.armorNerf != 100) {
				if (statRegisterEvent.stats instanceof CoreMaterialStats) {
					coreStats = (CoreMaterialStats) oldStats;
					newStats = new CoreMaterialStats(nerfInteger(Math.round(coreStats.durability), ConfigHandler.durabilityNerf), nerfFloat(coreStats.defense, ConfigHandler.armorNerf));
				} else if (statRegisterEvent.stats instanceof PlatesMaterialStats) {
					platesStats = (PlatesMaterialStats) oldStats;
					newStats = new PlatesMaterialStats(platesStats.modifier, nerfInteger(Math.round(platesStats.durability), ConfigHandler.durabilityNerf), nerfFloat(platesStats.toughness, ConfigHandler.armorNerf));
				} else if (statRegisterEvent.stats instanceof TrimMaterialStats) {
					trimStats = (TrimMaterialStats) oldStats;
					newStats = new TrimMaterialStats(nerfInteger(Math.round(trimStats.extraDurability), ConfigHandler.durabilityNerf));
				}
			}

			if (armoryMaterialsToTweak.containsKey(statRegisterEvent.material.identifier)) {
				String[] entries = armoryMaterialsToTweak.get(statRegisterEvent.material.identifier);

				if (statRegisterEvent.stats instanceof CoreMaterialStats) {
					coreStats = (CoreMaterialStats) (coreStats == null ? oldStats:newStats);
					newStats = new CoreMaterialStats(entries[1].equals("d") ? coreStats.durability:Float.parseFloat(entries[1]), entries[2].equals("d") ? coreStats.defense:Float.parseFloat(entries[2]));
				} else if (statRegisterEvent.stats instanceof PlatesMaterialStats) {
					platesStats = (PlatesMaterialStats) (platesStats == null ? oldStats:newStats);
					newStats = new PlatesMaterialStats(entries[3].equals("d") ? platesStats.modifier:Float.parseFloat(entries[3]), entries[4].equals("d") ? platesStats.durability:Float.parseFloat(entries[4]), entries[5].equals("d") ? platesStats.toughness:Float.parseFloat(entries[5]));
				} else if (statRegisterEvent.stats instanceof TrimMaterialStats) {
					trimStats = (TrimMaterialStats) (trimStats == null ? oldStats:newStats);
					newStats = new TrimMaterialStats(entries[6].equals("d") ? trimStats.extraDurability:Float.parseFloat(entries[6]));
				}
			}
		}

		if (Loader.isModLoaded("tinkerscompendium")) {
			HelmMaterialStats helmStats = null;
			ChestMaterialStats chestStats = null;
			LegsMaterialStats legsStats = null;
			FeetMaterialStats feetStats = null;
			ShieldMaterialStats shieldStats = null;

			if (ConfigHandler.durabilityNerf != 100 || ConfigHandler.armorNerf != 100) {
				if (statRegisterEvent.stats instanceof HelmMaterialStats) {
					helmStats = (HelmMaterialStats) oldStats;
					newStats = new HelmMaterialStats(nerfInteger(helmStats.durability, ConfigHandler.durabilityNerf), nerfInteger(helmStats.rating, ConfigHandler.armorNerf), nerfInteger(helmStats.toughness, ConfigHandler.armorNerf), helmStats.potency);
				} else if (statRegisterEvent.stats instanceof ChestMaterialStats) {
					chestStats = (ChestMaterialStats) oldStats;
					newStats = new ChestMaterialStats(nerfInteger(chestStats.durability, ConfigHandler.durabilityNerf), nerfInteger(chestStats.rating, ConfigHandler.armorNerf), nerfInteger(chestStats.toughness, ConfigHandler.armorNerf), chestStats.potency);
				} else if (statRegisterEvent.stats instanceof LegsMaterialStats) {
					legsStats = (LegsMaterialStats) oldStats;
					newStats = new LegsMaterialStats(nerfInteger(legsStats.durability, ConfigHandler.durabilityNerf), nerfInteger(legsStats.rating, ConfigHandler.armorNerf), nerfInteger(legsStats.toughness, ConfigHandler.armorNerf), legsStats.potency);
				} else if (statRegisterEvent.stats instanceof FeetMaterialStats) {
					feetStats = (FeetMaterialStats) oldStats;
					newStats = new FeetMaterialStats(nerfInteger(feetStats.durability, ConfigHandler.durabilityNerf), nerfInteger(feetStats.rating, ConfigHandler.armorNerf), nerfInteger(feetStats.toughness, ConfigHandler.armorNerf), feetStats.potency);
				} else if (statRegisterEvent.stats instanceof ShieldMaterialStats) {
					shieldStats = (ShieldMaterialStats) oldStats;
					newStats = new ShieldMaterialStats(nerfInteger(shieldStats.durability, ConfigHandler.durabilityNerf), shieldStats.percentBlocked);
				}
			}

			if (armorMaterialsToTweak.containsKey(statRegisterEvent.material.identifier)) {
				String[] entries = armorMaterialsToTweak.get(statRegisterEvent.material.identifier);

				if (statRegisterEvent.stats instanceof HelmMaterialStats) {
					helmStats = (HelmMaterialStats) (helmStats == null ? oldStats:newStats);
					newStats = new HelmMaterialStats(entries[1].equals("d") ? helmStats.durability:Integer.parseInt(entries[1]), entries[2].equals("d") ? helmStats.rating:Integer.parseInt(entries[2]), entries[3].equals("d") ? helmStats.toughness:Integer.parseInt(entries[3]), entries[4].equals("d") ? helmStats.potency:Float.parseFloat(entries[4]));
				} else if (statRegisterEvent.stats instanceof ChestMaterialStats) {
					chestStats = (ChestMaterialStats) (chestStats == null ? oldStats:newStats);
					newStats = new ChestMaterialStats(entries[5].equals("d") ? chestStats.durability:Integer.parseInt(entries[5]), entries[6].equals("d") ? chestStats.rating:Integer.parseInt(entries[6]), entries[7].equals("d") ? chestStats.toughness:Integer.parseInt(entries[7]), entries[8].equals("d") ? chestStats.potency:Float.parseFloat(entries[8]));
				} else if (statRegisterEvent.stats instanceof LegsMaterialStats) {
					legsStats = (LegsMaterialStats) (legsStats == null ? oldStats:newStats);
					newStats = new LegsMaterialStats(entries[9].equals("d") ? legsStats.durability:Integer.parseInt(entries[9]), entries[10].equals("d") ? legsStats.rating:Integer.parseInt(entries[10]), entries[11].equals("d") ? legsStats.toughness:Integer.parseInt(entries[11]), entries[12].equals("d") ? legsStats.potency:Float.parseFloat(entries[12]));
				} else if (statRegisterEvent.stats instanceof FeetMaterialStats) {
					feetStats = (FeetMaterialStats) (feetStats == null ? oldStats:newStats);
					newStats = new FeetMaterialStats(entries[13].equals("d") ? feetStats.durability:Integer.parseInt(entries[13]), entries[14].equals("d") ? feetStats.rating:Integer.parseInt(entries[14]), entries[15].equals("d") ? feetStats.toughness:Integer.parseInt(entries[15]), entries[16].equals("d") ? feetStats.potency:Float.parseFloat(entries[16]));
				}
			}

			if (shieldMaterialsToTweak.containsKey(statRegisterEvent.material.identifier)) {
				String[] entries = shieldMaterialsToTweak.get(statRegisterEvent.material.identifier);

				if (statRegisterEvent.stats instanceof ShieldMaterialStats) {
					shieldStats = (ShieldMaterialStats) (shieldStats == null ? oldStats:newStats);
					newStats = new ShieldMaterialStats(entries[1].equals("d") ? shieldStats.durability:Integer.parseInt(entries[1]), entries[2].equals("d") ? shieldStats.percentBlocked:Integer.parseInt(entries[2]));
				}
			}
		}

		if (newStats != null)
			statRegisterEvent.overrideResult(newStats);
	}

	int nerfInteger(int value, int percentage) {
		return Math.round(nerfFloat(value, percentage));
	}

	float nerfFloat(float value, int percentage) {
		float newValue = value * percentage/100;
		if (ConfigHandler.hardcoreNerfs) {
			if (percentage < 100) {
				if (newValue < 0)
					newValue += value;
			} else {
				if (newValue < 0)
					newValue -= value;
			}
		}
		return newValue;
	}
}
