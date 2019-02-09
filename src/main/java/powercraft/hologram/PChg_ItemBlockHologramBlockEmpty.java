package powercraft.hologram;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import powercraft.api.block.PC_ItemBlock;
import powercraft.api.registry.PC_LangRegistry.LangEntry;
import powercraft.api.registry.PC_LangRegistry;
import powercraft.api.registry.PC_MSGRegistry;

public class PChg_ItemBlockHologramBlockEmpty extends PC_ItemBlock {

	public PChg_ItemBlockHologramBlockEmpty(Block block) {
		super(block);
	}

	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		return true;
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean b) {
		list.add(PC_LangRegistry.tr("pc.notstable.desc"));
	}

}
