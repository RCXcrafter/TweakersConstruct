package com.rcx.tweaconstruct.proxy;

import com.rcx.tweaconstruct.ConfigHandler;
import com.rcx.tweaconstruct.tweaks.*;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.preInit(event.getSuggestedConfigurationFile());
		ToolVincibility.preInit();
		RemoveMaterials.preInit();
		//RemoveStats.preInit();
		StatTweaks.preInit();
		TraitTweaks.preInit();
		Modifiers.preInit();
	}

	public void init(FMLInitializationEvent event) {
		ToolpartCosts.init();
		MaterialAdditions.init();
		TraitTweaks.init();
	}

	public void postInit(FMLPostInitializationEvent event) {
		ConfigHandler.postInit();
	}
}
