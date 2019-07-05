package powercraft.hologram;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
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
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public List<LangEntry> getNames(ArrayList<LangEntry> names) {
		names.add(new LangEntry(getUnlocalizedName(), "Hologram Glasses"));
		return names;
	}

	@SideOnly(Side.CLIENT)
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		PChg_HologramGlassesOverlay hgo = new PChg_HologramGlassesOverlay();
		hgo.tickEvent();
	}

	@SideOnly(Side.CLIENT)
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks, boolean hasScreen, int mouseX, int mouseY){
		PChg_HologramGlassesOverlay hgo = new PChg_HologramGlassesOverlay();
		hgo.drawArea(PC_ClientUtils.mc(), player);
	}
	
}
