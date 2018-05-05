package ru.dannikinfo.powercraft.transport.belt;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;
import ru.dannikinfo.powercraft.api.utils.Direction;
import ru.dannikinfo.powercraft.api.utils.MathHelper;
import ru.dannikinfo.powercraft.api.utils.VecI;
import ru.dannikinfo.powercraft.api.utils.WorldData;
import ru.dannikinfo.powercraft.core.Main;
import ru.dannikinfo.powercraft.transport.tile.TileEntityRedirectionBelt;
import ru.dannikinfo.powercraft.transport.tile.TileEntitySeparationBelt;
import ru.dannikinfo.powercraft.transport.tile.TileEntitySplitter;



public class BeltSeparator extends BlockContainer implements ITileEntityProvider{
	
	public Object blockIcon_top_3;
	public Object blockIcon_top_2;
	public Object blockIcon_top_1;
	public Object blockIcon_top;
	public Object blockIcon_bottom;
	public Object blockIcon_side;
	
	public BeltSeparator(String name){
        super(Material.iron);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, BeltHelper.HEIGHT, 1.0F);
        setHardness(0.22F);
        setResistance(8.0F);
        setStepSound(Block.soundTypeMetal);
        setCreativeTab(Main.tabPowerCraft);
        setBlockName(name);
    }
	
	public void onBlockHarvested(World world, int x, int y, int z, int p_149681_5_, EntityPlayer p_149681_6_) {
		TileEntitySeparationBelt te = (TileEntitySeparationBelt) world.getTileEntity(x, y, z);
		for(int a = 0; a < te.basic.getSizeInventory(); a++){
			if(te.basic.getStackInSlot(a) != null){
				EntityItem ei = new EntityItem(world, x, y, z, te.basic.getStackInSlot(a));
				world.spawnEntityInWorld(ei);
			}
		}
	}
    
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return AxisAlignedBB.getBoundingBox(i, 0.0F + j, k, (i + 1), (j + BeltHelper.HEIGHT_COLLISION + 0.0F), (k + 1));
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
    {
        float f = 0;
        f = 0.0F + BeltHelper.HEIGHT_SELECTED;
        return AxisAlignedBB.getBoundingBox(i, 0.0F + j, k, (i + 1), j + f, (float) k + 1);
    }
    
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
        super.onBlockPlacedBy(world, x, y, z, entity, itemStack);
        int dir = (MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3);
        int[] r = {2, 5, 3, 4};
        world.setBlockMetadataWithNotify(x, y, z, r[dir], 3);
        String udid = BaseUtils.udid(x, y, z);
		WorldData data = WorldData.forWorld(world);
		NBTTagCompound nbt = data.getData();
		nbt.setInteger("front_" + udid, dir);
		data.markDirty();
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k)
    {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0F + BeltHelper.HEIGHT, 1.0F);
    }

    @Override
    public void setBlockBoundsForItemRender()
    {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0F + BeltHelper.HEIGHT, 1.0F);
    }

    @Override
    public int tickRate(World world)
    {
        return 1;
    }

    
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon_top_3 = reg.registerIcon(Main.MODID + ":" + "transport/" + "Belt_" + "separator_3");
		blockIcon_top_2 = reg.registerIcon(Main.MODID + ":" + "transport/" + "Belt_" + "separator_2");
		blockIcon_top_1 = reg.registerIcon(Main.MODID + ":" + "transport/" + "Belt_" + "separator_1");
		blockIcon_top = reg.registerIcon(Main.MODID + ":" + "transport/" + "Belt_" + "separator");
		blockIcon_bottom = reg.registerIcon(Main.MODID + ":" + "transport/" + "Belt_" + "down");
		blockIcon_side = reg.registerIcon(Main.MODID + ":" + "transport/" + "Belt_" + "side");
	}
	
    public IIcon getIcon(int side, int meta) {
        ForgeDirection dir = ForgeDirection.getOrientation(side);
        ForgeDirection block_dir;
        if (meta != 0)
            block_dir = ForgeDirection.getOrientation(meta);
        else
            block_dir = ForgeDirection.EAST;
        if (dir == ForgeDirection.UP){
        	if(meta == 0)return (IIcon) blockIcon_top;
        	if(meta == 2)return (IIcon) blockIcon_top_2;
        	if(meta == 5)return (IIcon) blockIcon_top_1;
        	if(meta == 3)return (IIcon) blockIcon_top;
        	if(meta == 4)return (IIcon) blockIcon_top_3;
        }
        if (dir == ForgeDirection.DOWN) return (IIcon) blockIcon_bottom;
        return (IIcon) blockIcon_side;
    }
    
    @Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntitySeparationBelt();
	}
    
    @Override
    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
    {
        VecI pos = new VecI(i, j, k);

        if (BeltHelper.isEntityIgnored(entity))
        {
            return;
        }

        TileEntitySeparationBelt tes = (TileEntitySeparationBelt) world.getTileEntity(i, j, k);
        Direction redir = tes.getDirection(entity);
        ForgeDirection fdirection = ForgeDirection.getOrientation(BaseUtils.getMD(world, pos));
        Direction direction = Direction.convertForgeDir(fdirection);
        redir = direction.rotate(redir);

        VecI pos_leading_to = pos.offset(redir.getOffset());
        
        if (entity instanceof EntityItem && BeltHelper.storeEntityItemAt(world, pos_leading_to, (EntityItem) entity, redir))
        {
            return;
        }
        
        boolean leadsToNowhere = BeltHelper.isBlocked(world, pos_leading_to);

        if (!leadsToNowhere)
        {
            BeltHelper.entityPreventDespawning(world, pos, true, entity);
        }

        leadsToNowhere = leadsToNowhere && BeltHelper.isBeyondStorageBorder(world, redir, pos, entity, BeltHelper.STORAGE_BORDER_LONG);
        BeltHelper.moveEntityOnBelt(world, pos, entity, true, !leadsToNowhere, redir, BeltHelper.MAX_HORIZONTAL_SPEED, BeltHelper.HORIZONTAL_BOOST);
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9){
        if (BeltHelper.blockActivated(world, i, j, k, entityplayer)){
            return true;
        }
        else{
        	entityplayer.openGui(Main.MODID, 10, world, i, j, k);
            return true;
        }
    }

}
