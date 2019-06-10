package powercraft.storage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.StatCollector;
import powercraft.hooklib.asm.Hook;
import powercraft.hooklib.asm.ReturnCondition;
import powercraft.hooklib.asm.Hook.ReturnValue;
import powercraft.storage.PCs_ItemCompressor;

public class PCs_TileEntityChest{
	
	@Hook(createMethod = true, injectOnExit = true,returnCondition = ReturnCondition.ALWAYS)
    public static boolean isUseableByPlayer(TileEntityChest te, EntityPlayer p_70300_1_, @ReturnValue boolean returnValue){
		if(p_70300_1_.getCurrentEquippedItem() != null && p_70300_1_.getCurrentEquippedItem().getTagCompound() != null && p_70300_1_.getCurrentEquippedItem().getTagCompound().getIntArray("posChest") != null) { 
			return true;
    	}else {
    		return returnValue;
    	}
    }

}
