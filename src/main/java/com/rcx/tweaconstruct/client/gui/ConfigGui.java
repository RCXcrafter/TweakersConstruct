package com.rcx.tweaconstruct.client.gui;

import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;
import java.util.List;

import com.rcx.tweaconstruct.TweakersConstruct;
import com.rcx.tweaconstruct.ConfigHandler;

public class ConfigGui extends GuiConfig {

	public ConfigGui(GuiScreen parentScreen) {
		super(parentScreen, getConfigElements(parentScreen), TweakersConstruct.MODID, false, false, "gui." + TweakersConstruct.MODID + ".config.title");
	}

	@SuppressWarnings("rawtypes")
	private static List<IConfigElement> getConfigElements(GuiScreen parent) {
		List<IConfigElement> list = new ArrayList<IConfigElement>();

		list.add(new ConfigElement(ConfigHandler.config.getCategory(ConfigHandler.misc.toLowerCase())));

		list.add(new ConfigElement(ConfigHandler.config.getCategory(ConfigHandler.stats.toLowerCase())));

		return list;
	}
}