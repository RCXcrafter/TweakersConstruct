package com.rcx.tweaconstruct.tweaks;

import java.lang.reflect.Field;

import com.rcx.tweaconstruct.ConfigHandler;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.IndestructibleEntityItem;
import slimeknights.tconstruct.library.tinkering.IndestructibleEntityItem.EventHandler;

public class ToolVincibility {

	public static void preInit() {
		if (ConfigHandler.toolVincibility)
			MinecraftForge.EVENT_BUS.register(new ToolVincibility());
	}

	@SubscribeEvent
	public void onToolDropped(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof IndestructibleEntityItem ) {
			IndestructibleEntityItem drop = (IndestructibleEntityItem) event.getEntity();

			World worldObj = event.getWorld();
			EntityItem newDrop = new EntityItem(worldObj, drop.posX, drop.posY, drop.posZ);

			newDrop.motionX = drop.motionX;
			newDrop.motionY = drop.motionY;
			newDrop.motionZ = drop.motionZ;

			NBTTagCompound tag = new NBTTagCompound();
			drop.writeToNBT(tag);
			newDrop.setPickupDelay(tag.getShort("PickupDelay"));

			worldObj.spawnEntityInWorld((EntityItem) newDrop);
			newDrop.setEntityItemStack(drop.getEntityItem());
			System.out.println(newDrop);

			event.setCanceled(true);
		}
	}
}
