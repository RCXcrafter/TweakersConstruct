package com.rcx.tweaconstruct;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = TweakersConstruct.MODID + "postload", version = TweakersConstruct.VERSION, dependencies = "after:*")
public class TweakersConstructPostload {
	@EventHandler
	public void postInit(FMLPostInitializationEvent postEvent) {
		TweakersConstruct.proxy.postInit(postEvent);
	}
}
