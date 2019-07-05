package powercraft.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import powercraft.api.network.PC_PacketHandler;
import powercraft.api.network.packet.PC_PacketSyncPlayerInv;
import powercraft.api.registry.PC_RecipeRegistry;
import powercraft.api.utils.PC_GlobalVariables;

public class PCco_CraftingToolCrafter {

	private static int MAX_RECURSION = 4;

	public static boolean tryToCraft(ItemStack product, EntityPlayer thePlayer) {
		if (thePlayer.capabilities.isCreativeMode || PC_GlobalVariables.config.getBoolean("cheats.survivalCheating")) {
			return true;
		}
		return craft(product, getPlayerInventory(thePlayer), new ArrayList<ItemStack>(), 0, thePlayer, false) > 0;
	}

	public static ItemStack[] getPlayerInventory(EntityPlayer thePlayer) {
		ItemStack[] inv = new ItemStack[thePlayer.inventory.getSizeInventory()];
		for (int i = 0; i < thePlayer.inventory.getSizeInventory(); i++) {
			inv[i] = thePlayer.inventory.getStackInSlot(i);
			if (inv[i] != null)
				inv[i] = inv[i].copy();
		}
		return inv;
	}

	public static void setPlayerInventory(ItemStack[] inv, EntityPlayer thePlayer) {
		for (int i = 0; i < thePlayer.inventory.getSizeInventory(); i++) {
			thePlayer.inventory.setInventorySlotContents(i, inv[i]);
			PC_PacketHandler.sendToServer(new PC_PacketSyncPlayerInv(i, inv[i]));
		}
	}

	private static ItemStack[] setTo(ItemStack[] inv, ItemStack[] inv1) {
		if (inv == null)
			inv = new ItemStack[inv1.length];
		for (int i = 0; i < inv.length; i++) {
			inv[i] = inv1[i];
		}
		return inv;
	}

	private static int testItem(ItemStack get, ItemStack[] is) {
		get = get.copy();
		for (int i = 0; i < is.length; i++) {
			if (get.equals(is[i])) {
				if (get.stackSize > is[i].stackSize) {
					get.splitStack(is[i].stackSize);
				} else {
					return 0;
				}
			}
		}
		return get.stackSize;
	}

	private static int testItem(List<ItemStack> l, ItemStack[] is, List<ItemStack> not, int rec,
			EntityPlayer thePlayer) {
		int i = 0;
		for (ItemStack stack : l) {
			if (testItem(stack, is) == 0)
				return i;
			i++;
		}

		for (ItemStack stack : l) {
			//System.out.println(is);
			int need = testItem(stack, is);
			System.out.println(need);
			ItemStack[] isc = setTo(null, is);
			int size = 0;
			List<ItemStack> notc = new ArrayList<ItemStack>(not);
			while (size < need) {
				//int nSize = craft(stack, isc, notc, rec, thePlayer);
				//if (nSize == 0) {
				//	size = 0;
				//	break;
				//}
				//size += craft(stack, isc, notc, rec, thePlayer);
			}
			if (size > 0) {
				System.out.println(1);
				stack = stack.copy();
				stack.stackSize = size;
				if (storeTo(stack, isc, thePlayer)) {
					setTo(is, isc);
					not.clear();
					not.addAll(notc);
					return i;
				}
			}
			i++;
		}
		return -1;
	}

	private static boolean storeTo(ItemStack get, ItemStack[] is, EntityPlayer thePlayer) {
		for (int i = 0; i < is.length; i++) {
			if (get.equals(is[i])) {
				int canPut = Math.min(thePlayer.inventory.getInventoryStackLimit(), is[i].getMaxStackSize())
						- is[i].stackSize;
				if (get.stackSize > canPut) {
					get.stackSize = (get.stackSize - canPut);
					is[i].stackSize += canPut;
				} else {
					is[i].stackSize += get.stackSize;
					return true;
				}
			}
		}
		return false;
	}

	private static void takeOut(ItemStack get, ItemStack[] is) {
		for (int i = 0; i < is.length; i++) {
			if (get.equals(is[i])) {
				if (get.stackSize > is[i].stackSize) {
					get.stackSize = (get.stackSize - is[i].stackSize);
					is[i] = null;
				} else {
					is[i].stackSize -= get.stackSize;
					if (is[i].stackSize == 0)
						is[i] = null;
					return;
				}
			}
		}
	}

	public static int craft(ItemStack craft, ItemStack[] is, List<ItemStack> not, int rec, EntityPlayer thePlayer, boolean type) {

		if (thePlayer.capabilities.isCreativeMode || PC_GlobalVariables.config.getBoolean("cheats.survivalCheating")) {
			return 1;
		}
		List<IRecipe> recipes = PC_RecipeRegistry.getRecipesForProduct(craft);
		//if (rec > MAX_RECURSION)
		//	return 0;
		if (not.contains(craft))
			return 0;
		not.add(craft);
		//rec++;
		for (IRecipe recipe : recipes) {
			ItemStack[] isc = setTo(null, is);
			List<ItemStack>[][] inp = PC_RecipeRegistry.getExpectedInput(recipe, -1, -1);
			List<List<ItemStack>> input = new ArrayList<List<ItemStack>>();
			if (inp == null)
				continue;
			for (int x = 0; x < inp.length; x++) {
				for (int y = 0; y < inp[x].length; y++) {
					if (inp[x][y] != null) {
						input.add(inp[x][y]);
					}
				}
			}

			boolean ret = false;
			boolean con = false;
			for (List<ItemStack> l : input) {
				for(int j = 0; j < 36; j++) {
					if(is[j] != null) {
						ret = is[j].getItem() == l.get(0).getItem();
						boolean ret2 = is[j].getItemDamage() == l.get(0).getItemDamage();
						boolean ret3 = is[j].stackSize >= l.get(0).stackSize;
						if (ret && ret2 && ret3) {
							if(type)
								thePlayer.inventory.decrStackSize(j, l.get(0).stackSize);
							return recipe.getRecipeOutput().stackSize;
						}else
							con = true;

					}

				}

			}
			if(con)
				continue;
		}
		return 0;
	}

}
