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

@Mod(modid = TweakersConstruct.MODID, version = TweakersConstruct.VERSION, dependencies = "after:tconstruct")

public class TweakersConstruct {

	@SidedProxy(clientSide = "com.rcx.tweaconstruct.proxy.ClientProxy", serverSide = "com.rcx.tweaconstruct.proxy.CommonProxy")
	public static CommonProxy proxy;
	public static final String MODID = "tweakersconstruct";
	public static final String VERSION = "1.0.0";

	/*public static final CreativeTabs creativeTabs = new CreativeTabs(MODID) {
		@SideOnly(Side.CLIENT)
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(CompressedBlockRegistry.compressedBlocks.get(0).compressedBlock);
		}

		@SideOnly(Side.CLIENT)
		@Override
		public int getIconItemDamage() {
			int firstMeta = 0;
			for (int i = 0; i < CompressedBlockRegistry.compressedBlocks.get(0).maxCompression; i++) {
				if (!CompressedBlockRegistry.compressedBlocks.get(0).existingLevels.contains(i + 1)) {
					firstMeta = i;
					break;
				}
			}
			return firstMeta;
		}
	};*/

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
