package powercraft.hologram;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import powercraft.api.annotation.PC_ClientServerSync;
import powercraft.api.tileentity.PC_TileEntity;

public class PChg_TileEntityHologramBlock extends PC_TileEntity {

	@PC_ClientServerSync(clientChangeAble = false)
	private Block containingBlock;
	// private int sw;
	// private int swMax;

	@Override
	public void create(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX,
			float hitY, float hitZ) {
		NBTTagCompound tag = stack.getTagCompound();
		if (tag != null) {
			ItemStack is = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Item"));
			containingBlock = Block.getBlockFromItem(is.getItem());
		} else {
			containingBlock = Blocks.air;
		}
	}
/*
	public void getData() {
		PC_PacketHandler.sendToAll(new PC_PacketSyncHBClient(this.sw, this.swMax, this));
	}

	public int getSwitchBlock() {

		return sw;
	}

	public void setSwitchBlock(int value) {
		sw = value;
	}

	public int getSwitchBlockMax() {
		if (worldObj.isRemote)
			PC_PacketHandler.sendToServer(new PC_PacketSyncHBFCTS(this));
		return swMax;
	}

	public void setSwitchBlockMax(int value) {
		swMax = value;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.sw = nbt.getInteger("SwitchBlock");
		this.swMax = nbt.getInteger("SwitchBlockMax");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("SwitchBlock", this.sw);
		nbt.setInteger("SwitchBlockMax", this.swMax);
	}
*/
	public Block getContainingBlock() {
		Block b = containingBlock;
		if (b == null) {
			b = Blocks.stone;
		}
		return b;
	}

	@Override
	public void updateEntity() {
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		markDirty();
	}

	@Override
	public boolean canUpdate() {
		return true;
	}

}
