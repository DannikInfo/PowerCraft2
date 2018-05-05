package ru.dannikinfo.powercraft.transport.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ru.dannikinfo.powercraft.transport.tile.TileEntitySplitter;

public class ContainerSplitter extends Container{

	TileEntitySplitter watcher;
	
	   public ContainerSplitter(IInventory playerInv, TileEntitySplitter watcher){
		   this.watcher = watcher;
	       int i = -18;
	       int j;
	       int k;
	       int index = 0;
	       for (j = 0; j < 5; ++j){
	           addSlotToContainer(new Slot(watcher.basic, index++, 21 + 0 * 18, 17 + j * 18));
	       }
	       for (j = 5; j < 10; ++j){
               addSlotToContainer(new Slot(watcher.basic, index++, 26 + 1 * 18, 17 + (j - 5) * 18));
	       }
	       for (j = 10; j < 15; ++j){
               addSlotToContainer(new Slot(watcher.basic, index++, 31 + 2 * 18, 17 + (j - 10) * 18));
	       }
	       for (j = 15; j < 20; ++j){
               addSlotToContainer(new Slot(watcher.basic, index++, 36 + 3 * 18, 17 + (j - 15) * 18));
	       }
	       for (j = 20; j < 25; ++j){
               addSlotToContainer(new Slot(watcher.basic, index++, 41 + 4 * 18, 17 + (j - 20) * 18));
	       }
	       for (j = 25; j < 30; ++j){
               addSlotToContainer(new Slot(watcher.basic, index++, 47 + 5 * 18, 17 + (j - 25) * 18));
	       }

	       /**
	        *   Этот цикл отвечает за вывод инвентаря игрока.
	        */
	       for (j = 0; j < 3; ++j)
	       {
	           for (k = 0; k < 9; ++k)
	           {
	               this.addSlotToContainer(new Slot(playerInv, k + j * 9 + 9, 8 + k * 18, 129 + j * 18 + i));
	           }
	       }

	       /**
	        *   Этот цикл отвечает за вывод hud бара игрока.
	        */
	       for (j = 0; j < 9; ++j)
	       {
	           this.addSlotToContainer(new Slot(playerInv, j, 8 + j * 18, 187 + i));
	       }
	   }

	   /**
	    *   Можно ли взаимодействовать с контейнером.
	    */
	   @Override
	   public boolean canInteractWith(EntityPlayer playerIn)
	   {
	       return true;
	   }

	   @Override
		public ItemStack transferStackInSlot(EntityPlayer player, int slot_i) {
			ItemStack is = null;
			Slot slot = (Slot)inventorySlots.get(slot_i);

			if (slot != null && slot.getHasStack()) {
				ItemStack is1 = slot.getStack();
				is = is1.copy();


				if (slot_i < watcher.basic.getSizeInventory()) {
					if (!mergeItemStack(is1, watcher.basic.getSizeInventory(), inventorySlots.size(), true)) return null;
				}else if (!mergeItemStack(is1, 0, watcher.basic.getSizeInventory(), false)) return null;
		
				if (is1.stackSize == 0) slot.putStack((ItemStack)null);
				else slot.onSlotChanged();
			}

			return is;
		}

}