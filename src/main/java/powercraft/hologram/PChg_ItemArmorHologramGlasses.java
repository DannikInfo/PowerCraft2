package powercraft.hologram;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercraft.api.item.PC_ItemArmor;
import powercraft.api.registry.PC_LangRegistry;
import powercraft.api.registry.PC_LangRegistry.LangEntry;
import powercraft.api.utils.PC_ClientUtils;

public class PChg_ItemArmorHologramGlasses extends PC_ItemArmor {

	public PChg_ItemArmorHologramGlasses(int id) {
		super(ArmorMaterial.IRON, HEAD, "glasses");
		setArmorTextureFile("glasses.png");
	}

	@Override
	public List<LangEntry> getNames(ArrayList<LangEntry> names) {
		names.add(new LangEntry(getUnlocalizedName(), "Hologram Glasses"));
		return names;
	}

	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		PChg_HologramGlassesOverlay hgo = new PChg_HologramGlassesOverlay();
		hgo.tickEvent();
		this.renderHelmetOverlay(stack, player, new
		ScaledResolution(PC_ClientUtils.mc(), 128, 128), 0, true, 0, 0);
	}

	@SideOnly(Side.CLIENT)
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution,
			float partialTicks, boolean hasScreen, int mouseX, int mouseY) {
		//PChg_HologramGlassesOverlay hgo = new PChg_HologramGlassesOverlay();
		//hgo.drawArea(PC_ClientUtils.mc(), player);
	}
	
	@Override
	public Item setCreativeTab(CreativeTabs _default) {
		return null;
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean b) {
		list.add(PC_LangRegistry.tr("pc.notstable.desc"));
	}

}
