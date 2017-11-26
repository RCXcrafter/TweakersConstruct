package com.rcx.tweaconstruct.proxy;

import com.rcx.tweaconstruct.ConfigHandler;
import com.rcx.tweaconstruct.tweaks.PatternCosts;
import com.rcx.tweaconstruct.tweaks.StatNerfs;
import com.rcx.tweaconstruct.tweaks.StatTweaks;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.preInit(event.getSuggestedConfigurationFile());
		PatternCosts.preInit();
	}

	public void init(FMLInitializationEvent event) {
		StatNerfs.init();
		StatTweaks.init();
	}

	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
