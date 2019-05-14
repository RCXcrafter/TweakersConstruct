package com.rcx.tweaconstruct.tweaks;

import java.util.HashMap;
import java.util.Map;

import com.rcx.tweaconstruct.ConfigHandler;

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
				if (entries.length != 11)
					continue;

				materialsToTweak.put(entries[0], entries);
			}

		if (ConfigHandler.arrowShaftStatTweaksList.length != 0)
			for (String entry : ConfigHandler.arrowShaftStatTweaksList) {
				String[] entries = entry.split(":");
				if (entries.length != 3)
					continue;

				shaftMaterialsToTweak.put(entries[0], entries);
			}

		if (ConfigHandler.bowStringStatTweaksList.length != 0)
			for (String entry : ConfigHandler.bowStringStatTweaksList) {
				String[] entries = entry.split(":");
				if (entries.length != 2)
					continue;

				stringMaterialsToTweak.put(entries[0], entries);
			}

		if (ConfigHandler.fletchingStatTweaksList.length != 0)
			for (String entry : ConfigHandler.fletchingStatTweaksList) {
				String[] entries = entry.split(":");
				if (entries.length != 3)
					continue;

				fletchingMaterialsToTweak.put(entries[0], entries);
			}

		if (Loader.isModLoaded("conarm") && ConfigHandler.armoryStatTweaksList.length != 0)
			for (String entry : ConfigHandler.armoryStatTweaksList) {
				String[] entries = entry.split(":");
				if (entries.length != 7)
					continue;

				armoryMaterialsToTweak.put(entries[0], entries);
			}

		if (Loader.isModLoaded("tinkerscompendium")) {
			if (ConfigHandler.armorStatTweaksList.length != 0)
				for (String entry : ConfigHandler.armorStatTweaksList) {
					String[] entries = entry.split(":");
					if (entries.length != 17)
						continue;

					armorMaterialsToTweak.put(entries[0], entries);
				}

			if (ConfigHandler.shieldStatTweaksList.length != 0)
				for (String entry : ConfigHandler.shieldStatTweaksList) {
					String[] entries = entry.split(":");
					if (entries.length != 3)
						continue;

					shieldMaterialsToTweak.put(entries[0], entries);
				}
		}
	}

	@SubscribeEvent
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
				newStats = new HeadMaterialStats(nerfDurability(headStats.durability), headStats.miningspeed*ConfigHandler.mineSpeedNerf/100, headStats.attack*ConfigHandler.attackNerf/100, headStats.harvestLevel);
			} else if (statRegisterEvent.stats instanceof HandleMaterialStats) {
				handleStats = (HandleMaterialStats) oldStats;
				newStats = new HandleMaterialStats(handleStats.modifier, nerfDurability(handleStats.durability));
			} else if (statRegisterEvent.stats instanceof ExtraMaterialStats) {
				extraStats = (ExtraMaterialStats) oldStats;
				newStats = new ExtraMaterialStats(nerfDurability(extraStats.extraDurability));
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
					newStats = new CoreMaterialStats(nerfDurability(Math.round(coreStats.durability)), coreStats.defense*ConfigHandler.armorNerf/100);
				} else if (statRegisterEvent.stats instanceof PlatesMaterialStats) {
					platesStats = (PlatesMaterialStats) oldStats;
					newStats = new PlatesMaterialStats(platesStats.modifier, nerfDurability(Math.round(platesStats.durability)), platesStats.toughness*ConfigHandler.armorNerf/100);
				} else if (statRegisterEvent.stats instanceof TrimMaterialStats) {
					trimStats = (TrimMaterialStats) oldStats;
					newStats = new TrimMaterialStats(nerfDurability(Math.round(trimStats.extraDurability)));
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

			if (ConfigHandler.durabilityNerf != 100) {
				if (statRegisterEvent.stats instanceof HelmMaterialStats) {
					helmStats = (HelmMaterialStats) oldStats;
					newStats = new HelmMaterialStats(nerfDurability(helmStats.durability), helmStats.rating, helmStats.toughness, helmStats.potency);
				} else if (statRegisterEvent.stats instanceof ChestMaterialStats) {
					chestStats = (ChestMaterialStats) oldStats;
					newStats = new ChestMaterialStats(nerfDurability(chestStats.durability), chestStats.rating, chestStats.toughness, chestStats.potency);
				} else if (statRegisterEvent.stats instanceof LegsMaterialStats) {
					legsStats = (LegsMaterialStats) oldStats;
					newStats = new LegsMaterialStats(nerfDurability(legsStats.durability), legsStats.rating, legsStats.toughness, legsStats.potency);
				} else if (statRegisterEvent.stats instanceof FeetMaterialStats) {
					feetStats = (FeetMaterialStats) oldStats;
					newStats = new FeetMaterialStats(nerfDurability(feetStats.durability), feetStats.rating, feetStats.toughness, feetStats.potency);
				} else if (statRegisterEvent.stats instanceof ShieldMaterialStats) {
					shieldStats = (ShieldMaterialStats) oldStats;
					newStats = new ShieldMaterialStats(nerfDurability(shieldStats.durability), shieldStats.percentBlocked);
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

	int nerfDurability(int durability) {
		int newDurability = durability * ConfigHandler.durabilityNerf/100;
		if (ConfigHandler.hardcoreNerfs) {
			if (ConfigHandler.durabilityNerf < 100) {
				if (newDurability < 0)
					newDurability += durability;
			} else {
				if (newDurability < 0)
					newDurability -= durability;
			}
		}
		return newDurability;
	}
}
