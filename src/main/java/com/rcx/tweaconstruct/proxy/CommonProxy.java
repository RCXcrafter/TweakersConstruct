package com.rcx.tweaconstruct.proxy;

import com.rcx.tweaconstruct.ConfigHandler;
import com.rcx.tweaconstruct.tweaks.*;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.preInit(event.getSuggestedConfigurationFile());
		PatternCosts.preInit();
		ToolVincibility.preInit();
		RemoveMaterials.preInit();
	}

	public void init(FMLInitializationEvent event) {
		
	}

	public void postInit(FMLPostInitializationEvent event) {
		StatNerfs.postInit();
		StatTweaks.postInit();
	}
}
