package powercraft.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import powercraft.api.network.PC_IPacketHandler;
import powercraft.api.registry.PC_RecipeRegistry;
import powercraft.api.utils.PC_GlobalVariables;

public class PCco_CraftingToolCrafter implements PC_IPacketHandler {

	private static int MAX_RECURSION = 4;

	public static boolean tryToCraft(ItemStack product, EntityPlayer thePlayer) {
		if (thePlayer.capabilities.isCreativeMode
				|| PC_GlobalVariables.config
						.getBoolean("cheats.survivalCheating")) {
			return true;
		}
		return craft(product, getPlayerInventory(thePlayer),
				new ArrayList<ItemStack>(), 0, thePlayer) > 0;
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

	public static void setPlayerInventory(ItemStack[] inv,
			EntityPlayer thePlayer) {
		for (int i = 0; i < thePlayer.inventory.getSizeInventory(); i++) {
			thePlayer.inventory.setInventorySlotContents(i, inv[i]);
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

	private static int testItem(List<ItemStack> l, ItemStack[] is,
			List<ItemStack> not, int rec, EntityPlayer thePlayer) {
		int i = 0;
		for (ItemStack stack : l) {
			if (testItem(stack, is) == 0)
				return i;
			i++;
		}
		for (ItemStack stack : l) {
			int need = testItem(stack, is);
			ItemStack[] isc = setTo(null, is);
			int size = 0;
			List<ItemStack> notc = new ArrayList<ItemStack>(not);
			while (size < need) {
				int nSize = craft(stack, isc, notc, rec,
						thePlayer);
				if (nSize == 0) {
					size = 0;
					break;
				}
				size += craft(stack, isc, notc, rec, thePlayer);
			}
			if (size > 0) {
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

	private static boolean storeTo(ItemStack get, ItemStack[] is,
			EntityPlayer thePlayer) {
		for (int i = 0; i < is.length; i++) {
			if (get.equals(is[i])) {
				int canPut = Math.min(
						thePlayer.inventory.getInventoryStackLimit(),
						is[i].getMaxStackSize())
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

	public static int craft(ItemStack craft, ItemStack[] is, List<ItemStack> not, int rec, EntityPlayer thePlayer) {
		if(thePlayer.capabilities.isCreativeMode || PC_GlobalVariables.config
						.getBoolean("cheats.survivalCheating")){
			return 1;
		}
		List<IRecipe> recipes = PC_RecipeRegistry.getRecipesForProduct(craft);
		if (rec > MAX_RECURSION)
			return 0;
		if (not.contains(craft))
			return 0;
		not.add(craft);
		rec++;
		for (IRecipe recipe : recipes) {
			ItemStack[] isc = setTo(null, is);
			List<ItemStack>[][] inp = PC_RecipeRegistry.getExpectedInput(
					recipe, -1, -1);
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

			int ret;
			boolean con = false;
			for (List<ItemStack> l : input) {
				ret = testItem(l, isc, not, rec, thePlayer);
				if (ret < 0) {
					con = true;
					break;
				}
				takeOut(l.get(ret), isc);
			}
			if (con)
				continue;

			setTo(is, isc);
			return recipe.getRecipeOutput().stackSize;
		}
		return 0;
	}

	@Override
	public boolean handleIncomingPacket(EntityPlayer player, Object[] o) {
		Item item = (Item) o[0];
		int damage = (Integer) o[1];
		ItemStack is = new ItemStack(item, 1, damage);
		ItemStack[] pi = PCco_CraftingToolCrafter.getPlayerInventory(player);
		is.stackSize = PCco_CraftingToolCrafter.craft(is, pi, new ArrayList<ItemStack>(), 0, player);
		if(is.stackSize>0){
			ItemStack pis = player.inventory.getItemStack();
			if(pis==null){
				PCco_CraftingToolCrafter.setPlayerInventory(pi, player);
				player.inventory.setItemStack(is);
			}else if(pis.isItemEqual(is)){
				if(pis.stackSize+is.stackSize<=is.getMaxStackSize()){
					pis.stackSize += is.stackSize;
					PCco_CraftingToolCrafter.setPlayerInventory(pi, player);
					player.inventory.setItemStack(pis);
				}
			}
		}
		return false;
	}

}
