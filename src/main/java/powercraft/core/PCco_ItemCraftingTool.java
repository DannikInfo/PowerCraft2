package powercraft.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercraft.api.item.PC_Item;
import powercraft.api.registry.PC_LangRegistry.LangEntry;

public class PCco_ItemCraftingTool extends PC_Item {
	public PCco_ItemCraftingTool(int ids) {
		super("craftingtool");
		setMaxDamage(0);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabTools);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
		//if (!world.isRemote) {
		//	PC_GresRegistry.openGres("CraftingTool", entityplayer, null);
		//} Нет ничего более постоянного чем временнное))
		if (!world.isRemote)
			player.openContainer = new PCco_ContainerCraftingTool(player.inventory, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
		else
			Minecraft.getMinecraft().displayGuiScreen(new GuiCrafting(player.inventory, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ));
		
		return itemstack;
	}

	@Override
	public List<LangEntry> getNames(ArrayList<LangEntry> names) {
		names.add(new LangEntry(getUnlocalizedName(), "Crafting Tool"));
		return names;
	}
}
