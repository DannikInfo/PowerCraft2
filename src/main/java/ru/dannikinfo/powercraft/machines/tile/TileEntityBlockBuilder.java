package ru.dannikinfo.powercraft.machines.tile;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRail;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemReed;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.api.entity.FakePlayer;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;
import ru.dannikinfo.powercraft.api.utils.VecI;


public class TileEntityBlockBuilder extends TileEntity{

	private static Random rand = new Random();
	private FakePlayer fakeplayer;
	public InventoryBasic basic;

	public TileEntityBlockBuilder() {
	}
	
	/**
	 * @return forge - can update; false
	 */
	@Override
	public boolean canUpdate() {
		return false;
	}
	
	@Override
	public void setWorldObj(World world) {
		super.setWorldObj(world);
		fakeplayer = new FakePlayer(world);
	}

	/**
	 * Use some random item from the dispenser inventory.
	 */
	
	public boolean useItem(VecI coord) {
		int i = -1;
		int j = 1;
		for (int k = 0; k < basic.getSizeInventory(); k++) {
			if (basic.getStackInSlot(k) != null && rand.nextInt(j++) == 0) {
				i = k;
			}
		}
		if (i >= 0) {
			int state = try2useItem(basic.getStackInSlot(i).copy(), coord);
			if (state == -1) {
				return true;
			}
			if (state == 0) {
				return false;
			}
			if(!worldObj.isRemote){
				if (state == 1) {
					basic.decrStackSize(i, 1);
				}
				if (state == 2) {
					basic.getStackInSlot(i).damageItem(1, fakeplayer);
					if (basic.getStackInSlot(i) != null && basic.getStackInSlot(i).stackSize <= 0) {
						basic.setInventorySlotContents(i, null);
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Use item, return state flag
	 * 
	 * @param itemstack stack to use
	 * @param dist distance form device
	 * @return 0 = nothing happened to the stack. 1 = decrement stack size, 2 =
	 *         damage item
	 */
	private int try2useItem(ItemStack itemstack, VecI front) {

		int l = BaseUtils.getMD(worldObj, front) & 7;

		VecI below = new VecI(front.offset(0, -1, 0));
		VecI above = new VecI(front.offset(0, 1, 0));


		//int idFront = BaseUtils.getBID(worldObj, front);
		int metaFront = BaseUtils.getMD(worldObj, front);

		//int idBelow = BaseUtils.getBID(worldObj, below);
		int metaBelow = BaseUtils.getMD(worldObj, below);

		//int idAbove = BaseUtils.getBID(worldObj, above);
		int metaAbove = BaseUtils.getMD(worldObj, above);

		//int id = idFront;

		// try to put minecart
		if (itemstack.getItem() instanceof ItemMinecart) {
			
			//if (PC_BlockRegistry.isBlock(worldObj, front, "PCtr_BlockBelt") || Block.blocksList[id] instanceof BlockRail) {
				if (!worldObj.isRemote) {
					worldObj.spawnEntityInWorld(EntityMinecart.createMinecart(worldObj, front.x + 0.5F, front.y + 0.5F, front.z + 0.5F,
							((ItemMinecart) itemstack.getItem()).minecartType));
				}
				return 1;
			//}
		}

		// ending block
		//if (id == 49
		//		|| id == 7
		//		|| id == 98
		//		/** TODO || (PC_MSGRegistry.hasFlag(worldObj, front, PC_Utils.HARVEST_STOP))*/) {
			//return -1;
		//}

		// try to place front
		if (itemstack.getItem() instanceof ItemBlock) {

			/** TODO if(PC_MSGRegistry.hasFlag(itemstack, PC_Utils.NO_BUILD)){
				return 0;
			}*/
			
			ItemBlock item = ((ItemBlock) itemstack.getItem());

			//if (BlockcanPlaceBlockAt(worldObj, front.x, front.y, front.z)) {
				worldObj.setBlock(front.x, front.y, front.z, Block.getBlockFromItem(item), item.getMetadata(itemstack.getItemDamage()), 0);
				return 1;
			//}

			/*if (isEmptyBlock(idFront) && Block.blocksList[item.itemID].canPlaceBlockAt(worldObj, x + incX * 2, y, z + incZ * 2)) {
				PC_Utils.setBID(worldObj, x + incX * 2, y, z + incZ * 2, item.itemID, item.getMetadata(itemstack.getItemDamage()));
				return 1;
			}*/

			//return 0;
		}

		// use on front block (usually bonemeal on crops)
		if (!(itemstack.getItem() instanceof ItemReed)) {//!PC_Utils.isBlockReplaceable(worldObj, front) && 
			int dmgOrig = itemstack.getItemDamage();
			int sizeOrig = itemstack.stackSize;

			if (itemstack.getItem().onItemUse(itemstack, fakeplayer, worldObj, front.x, front.y, front.z, 1, 0.0f, 0.0f, 0.0f)) {
				
				if (itemstack.getItem() instanceof ItemMonsterPlacer) {
					return 1;
				}

				//int idFrontNew = PC_Utils.getBID(worldObj, front);
				int metaFrontNew = BaseUtils.getMD(worldObj, front);
				//int idAboveNew = BaseUtils.getBID(worldObj, above);
				int metaAboveNew = BaseUtils.getMD(worldObj, above);

				int dmgNew = itemstack.getItemDamage();
				int sizeNew = itemstack.stackSize;

				// if not bonemeal, or if target block was changed
				if (!(itemstack.getItem() == Items.dye && itemstack.getItemDamage() == 15)
						|| ( metaFront != metaFrontNew || metaAbove != metaAboveNew)) {
					if (dmgOrig != dmgNew) {
						return 2;
					}
					if (sizeOrig != sizeNew) {
						return 1;
					}
				}
			}
		}

		// use below
		//if (//BaseUtils.isBlockReplaceable(worldObj, front) //&& !BaseUtils.isBlockReplaceable(worldObj, below)
		//		) {
			int dmg1 = itemstack.getItemDamage();
			int size1 = itemstack.stackSize;

			if (itemstack.getItem().onItemUse(itemstack, fakeplayer, worldObj, below.x, below.y, below.z, 1, 0.0f, 0.0f, 0.0f)) {

				if (itemstack.getItem() instanceof ItemMonsterPlacer) {
					return 1;
				}

				//int idBelowNew = BaseUtils.getBID(worldObj, below);
				int metaBelowNew = BaseUtils.getMD(worldObj, below);
			//	int idFrontNew = BaseUtils.getBID(worldObj, front);
				int metaFrontNew = BaseUtils.getMD(worldObj, front);

				int dmg2 = itemstack.getItemDamage();
				int size2 = itemstack.stackSize;
				// if not bonemeal, or if target block was changed
				if (!(itemstack.getItem()== Items.dye&& itemstack.getItemDamage() == 15)
						|| ( metaBelow != metaBelowNew || metaFront != metaFrontNew)) {
					if (dmg1 != dmg2) {
						return 2;
					}
					if (size1 != size2) {
						return 1;
					}
				}
			}
		//}

		return 0;
	}

}
