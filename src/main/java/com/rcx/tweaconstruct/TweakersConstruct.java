package com.rcx.tweaconstruct;

import com.rcx.tweaconstruct.proxy.CommonProxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = TweakersConstruct.MODID, version = TweakersConstruct.VERSION, dependencies = "before:tconstruct")

public class TweakersConstruct {

	@SidedProxy(clientSide = "com.rcx.tweaconstruct.proxy.ClientProxy", serverSide = "com.rcx.tweaconstruct.proxy.CommonProxy")
	public static CommonProxy proxy;
	public static final String MODID = "tweakersconstruct";
	public static final String VERSION = "1.10.2-1.1.0";

	@EventHandler
	public void preInit(FMLPreInitializationEvent preEvent) {
		proxy.preInit(preEvent);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent postEvent) {
		proxy.postInit(postEvent);
	}
}
