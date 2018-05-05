package ru.dannikinfo.powercraft.api.network;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import ru.dannikinfo.powercraft.core.BlocksCore;
import ru.dannikinfo.powercraft.core.crystal.RendererCrystal;
import ru.dannikinfo.powercraft.core.crystal.RendererItemCrystal;
import ru.dannikinfo.powercraft.core.crystal.TileEntityCrystal;
import ru.dannikinfo.powercraft.deco.BlocksDeco;
import ru.dannikinfo.powercraft.deco.chimeny.RendererChimeny;
import ru.dannikinfo.powercraft.deco.chimeny.RendererItemChimeny;
import ru.dannikinfo.powercraft.deco.chimeny.TileEntityChimeny;
import ru.dannikinfo.powercraft.deco.ironframe.RendererIronFrame;
import ru.dannikinfo.powercraft.deco.ironframe.RendererItemIronFrame;
import ru.dannikinfo.powercraft.deco.ironframe.TileEntityIronFrame;
import ru.dannikinfo.powercraft.deco.platform.RendererItemPlatform;
import ru.dannikinfo.powercraft.deco.platform.RendererPlatform;
import ru.dannikinfo.powercraft.deco.platform.TileEntityPlatform;
import ru.dannikinfo.powercraft.deco.stairs.RendererItemStairs;
import ru.dannikinfo.powercraft.deco.stairs.RendererStairs;
import ru.dannikinfo.powercraft.deco.stairs.TileEntityStairs;
import ru.dannikinfo.powercraft.light.BlocksLight;
import ru.dannikinfo.powercraft.light.laser.RendererItemLaser;
import ru.dannikinfo.powercraft.light.laser.RendererLaser;
import ru.dannikinfo.powercraft.light.laser.TileEntityLaser;
import ru.dannikinfo.powercraft.light.mirror.RendererItemMirror;
import ru.dannikinfo.powercraft.light.mirror.RendererMirror;
import ru.dannikinfo.powercraft.light.mirror.TileEntityMirror;
import ru.dannikinfo.powercraft.logic.BlocksLogic;
import ru.dannikinfo.powercraft.logic.detector.RendererDetector;
import ru.dannikinfo.powercraft.logic.detector.RendererDetectorOff;
import ru.dannikinfo.powercraft.logic.detector.RendererItemDetector;
import ru.dannikinfo.powercraft.logic.detector.TileEntityDetector;
import ru.dannikinfo.powercraft.logic.detector.TileEntityDetectorOff;
import ru.dannikinfo.powercraft.teleporter.BlocksTeleporter;
import ru.dannikinfo.powercraft.teleporter.RendererItemTeleporter;
import ru.dannikinfo.powercraft.teleporter.RendererTeleporter;
import ru.dannikinfo.powercraft.teleporter.TileEntityTeleporter;

public class ClientProxy extends CommonProxy {

	public void preInit() {
		super.preInit();
	}

	public void init() {
		super.init();
		
		//Blocks
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTeleporter.class, new RendererTeleporter());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityIronFrame.class, new RendererIronFrame());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrystal.class, new RendererCrystal());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaser.class, new RendererLaser());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChimeny.class, new RendererChimeny());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDetector.class, new RendererDetector());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDetectorOff.class, new RendererDetectorOff());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlatform.class, new RendererPlatform());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStairs.class, new RendererStairs());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMirror.class, new RendererMirror());
		
		//items
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksTeleporter.teleporter), (IItemRenderer) new RendererItemTeleporter());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksDeco.ironframe), (IItemRenderer) new RendererItemIronFrame());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksCore.crystal), (IItemRenderer) new RendererItemCrystal());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksLight.laser), (IItemRenderer) new RendererItemLaser());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksDeco.chimeny), (IItemRenderer) new RendererItemChimeny());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksLogic.detector), (IItemRenderer) new RendererItemDetector());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksLogic.detectoroff), (IItemRenderer) new RendererItemDetector());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksDeco.platform), (IItemRenderer) new RendererItemPlatform());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksDeco.stairs), (IItemRenderer) new RendererItemStairs());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksLight.mirror), (IItemRenderer) new RendererItemMirror());
	}
	

	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		// Note that if you simply return 'Minecraft.getMinecraft().thePlayer',
		// your packets will not work as expected because you will be getting a
		// client player even when you are on the server!
		// Sounds absurd, but it's true.

		// Solution is to double-check side before returning the player:
		return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx));
	}
	
	@Override
	public World getClientWorld(){
		return FMLClientHandler.instance().getClient().theWorld;
	}



}
