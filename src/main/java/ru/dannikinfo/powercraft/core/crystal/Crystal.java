package ru.dannikinfo.powercraft.core.crystal;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;
import ru.dannikinfo.powercraft.api.utils.WorldData;
import ru.dannikinfo.powercraft.core.BlocksCore;
import ru.dannikinfo.powercraft.core.ItemsCore;
import ru.dannikinfo.powercraft.core.Main;
import ru.dannikinfo.powercraft.machines.chunker.TileEntityChunker;

public class Crystal extends Block implements ITileEntityProvider {

    public static final String[] metadata = new String[] {
            "Cyan",
            "DarkBlue",
            "Green",
            "LightBlue",
            "Orange",
            "Purple",
            "Red",
            "Yellow",  
        };
	public NBTTagCompound nbt = new NBTTagCompound();
	public boolean isPowered;
	int c = 0;
	public Crystal() {
		super(Material.glass);
		setCreativeTab(Main.tabPowerCraft);
		setHardness(1F);
		setBlockTextureName("glass");
		setBlockName(Main.MODID + ".Crystal");
		
	}
	
    public int damageDropped(int par1)
    {
        return par1;
    }
	
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return true;
    }
    
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityCrystal();
	}

	public int getRenderType() {
		return -1;
	}
	
    @Override
    public int getRenderBlockPass()
    {
        return 1;
    }
	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}
	
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        for (int j = 0; j < metadata.length; ++j) {
            
            list.add(new ItemStack(item, 1, j));
            
        }
    }
    
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int n1, float f1, float f2, float f3){
		/*String udid = BaseUtils.udid(x, y, z); DO NOT USE IT!!!!!!!!! IT ONE VERY VERY VERY BIG BUG!
		WorldData data = WorldData.forWorld(world);
		NBTTagCompound tag = data.getData();
		int stats = tag.getInteger("status_" + udid);
		if(!world.isRemote){
		    	if(player.inventory.getCurrentItem() != null){ 
		    		Item item = player.inventory.getCurrentItem().getItem();
			    	if(item == ItemsCore.ActivationCrystal){
				    	if(stats == 0){
				    		if(world.getBlock(x, y - 1, z) == Blocks.redstone_block){
					    		tag.setInteger("status_" + udid, 1);
						    	isPowered = true;
						    	//Z+
						    	for(int i = 0; i <= 4; i++) {
						    		if(world.getBlock(x, y, z + i + 1) == BlocksCore.crystal) {
						    			for(int a = 0; a < 10; a++) {
						    				if(world.getBlock(x, y - 1 - a, z + i) == Blocks.air) {
						    					for(int b = 1; b <= i; b++) {
						    						world.setBlock(x, y - 1 - a, z + b, BlocksCore.security);
						    					}
						    				}
						    			}
						    		}
						    	}
						    	//Z-
						    	for(int i = 0; i <= 4; i++) {
						    		if(world.getBlock(x, y, z - i - 1) == BlocksCore.crystal) {
						    			for(int a = 0; a < 10; a++) {
						    				if(world.getBlock(x, y - 1 - a, z - i) == Blocks.air) {
						    					for(int b = 1; b <= i; b++) {
						    						world.setBlock(x, y - 1 - a, z - b, BlocksCore.security);
						    					}
						    				}
						    			}
						    		}
						    	}
						    	//X+
						    	for(int i = 0; i <= 4; i++) {
						    		if(world.getBlock(x + i + 1, y, z) == BlocksCore.crystal) {
						    			for(int a = 0; a < 10; a++) {
						    				if(world.getBlock(x + i, y - 1 - a, z) == Blocks.air) {
						    					for(int b = 1; b <= i; b++) {
						    						world.setBlock(x + b, y - 1 - a, z, BlocksCore.security);
						    					}
						    				}
						    			}
						    		}
						    	}
						    	//X-
						    	for(int i = 0; i <= 4; i++) {
						    		if(world.getBlock(x - i - 1, y, z) == BlocksCore.crystal) {
						    			for(int a = 0; a < 10; a++) {
						    				if(world.getBlock(x - i, y - 1 - a, z) == Blocks.air) {
						    					for(int b = 1; b <= i; b++) {
						    						world.setBlock(x - b, y - 1 - a, z, BlocksCore.security);
						    					}
						    				}
						    			}
						    		}
						    	}
						    	//square pole
						    	int a = 0, b = 0;

						    while(a <= 10 && b <= 4) {
						    		if(c < 50) {
							    		if(world.getBlock(x - b - 1, y, z) == BlocksCore.crystal) {
							    			udid = BaseUtils.udid(x - b - 1, y, z);
							    			int st = tag.getInteger("status_" + udid);
							    			if(st == 0) {
							    				a++;
							    				onBlockActivated(world, x - b - 1, y, z, player, n1, f1, f2, f3);
							    			}
							    		}
							    		if(world.getBlock(x + b + 1, y, z) == BlocksCore.crystal) {
							    			udid = BaseUtils.udid(x + b + 1, y, z);
							    			int st = tag.getInteger("status_" + udid);
							    			if(st == 0) {
							    				a++;
							    				onBlockActivated(world, x + b + 1, y, z, player, n1, f1, f2, f3);
							    			}
							    		}
							    		if(world.getBlock(x, y, z - b - 1) == BlocksCore.crystal) {
							    			udid = BaseUtils.udid(x, y, z - b - 1);
							    			int st = tag.getInteger("status_" + udid);
							    			if(st == 0) {
							    				a++;
								    			onBlockActivated(world, x, y, z - b - 1, player, n1, f1, f2, f3);
							    			}
							    		}
							    		if(world.getBlock(x, y, z + b + 1) == BlocksCore.crystal) {
							    			udid = BaseUtils.udid(x, y, z + b + 1);
							    			int st = tag.getInteger("status_" + udid);
							    			if(st == 0) {
							    				a++;
								    			onBlockActivated(world, x, y, z + b + 1, player, n1, f1, f2, f3);
							    			}
							    		}
						    		}
						    		b++;
						    		c++;
						    	}
						    if(c >= 50) 
						    		c = 0;
				    			//ßtag.setInteger("crystal_count", c);
				    			System.out.println(c);
				    			data.markDirty();
				    		}
					}
				    	if(stats == 1){
				    		if(world.getBlock(x, y - 1, z) == Blocks.redstone_block){
					    		tag.setInteger("status_" + udid, 0);
						    	isPowered = false;
						    	//Z+
						    	for(int i = 0; i <= 4; i++) {
						    		if(world.getBlock(x, y, z + i + 1) == BlocksCore.crystal) {
						    			for(int a = 0; a < 10; a++) {
						    				if(world.getBlock(x, y - 1 - a, z + i) == BlocksCore.security) {
						    					for(int b = 1; b <= i; b++) {
						    						world.setBlock(x, y - 1 - a, z + b, Blocks.air);
						    					}
						    				}
						    			}
						    		}
						    	}
						    	//Z-
						    	for(int i = 0; i <= 4; i++) {
						    		if(world.getBlock(x, y, z - i - 1) == BlocksCore.crystal) {
						    			for(int a = 0; a < 10; a++) {
						    				if(world.getBlock(x, y - 1 - a, z - i) == BlocksCore.security) {
						    					for(int b = 1; b <= i; b++) {
						    						world.setBlock(x, y - 1 - a, z - b, Blocks.air);
						    					}
						    				}
						    			}
						    		}
						    	}
						    	//X+
						    	for(int i = 0; i <= 4; i++) {
						    		if(world.getBlock(x + i + 1, y, z) == BlocksCore.crystal) {
						    			for(int a = 0; a < 10; a++) {
						    				if(world.getBlock(x + i, y - 1 - a, z) == BlocksCore.security) {
						    					for(int b = 1; b <= i; b++) {
						    						world.setBlock(x + b, y - 1 - a, z, Blocks.air);
						    					}
						    				}
						    			}
						    		}
						    	}
						    	//X-
						    	for(int i = 0; i <= 4; i++) {
						    		if(world.getBlock(x - i - 1, y, z) == BlocksCore.crystal) {
						    			for(int a = 0; a < 10; a++) {
						    				if(world.getBlock(x - i, y - 1 - a, z) == BlocksCore.security) {
						    					for(int b = 1; b <= i; b++) {
						    						world.setBlock(x - b, y - 1 - a, z, Blocks.air);
						    					}
						    				}
						    			}
						    		}
						    	}
						    	//square pole
						    	int a = 0, b = 0;
						    a =	tag.getInteger("crystal_count_del");
						    	while(a <= 10 && b <= 4) {
						    		if(world.getBlock(x - b - 1, y, z) == BlocksCore.crystal) {
						    			udid = BaseUtils.udid(x - b - 1, y, z);
						    			int st = tag.getInteger("status_" + udid);
						    			if(st == 1) {
						    				a++;
									    	if(a >= 10)
									    		a = 0;
									    	tag.setInteger("crystal_count_del", a);
										data.markDirty();
						    				onBlockActivated(world, x - b - 1, y, z, player, n1, f1, f2, f3);
						    			}
						    		}
						    		if(world.getBlock(x + b + 1, y, z) == BlocksCore.crystal) {
						    			udid = BaseUtils.udid(x + b + 1, y, z);
						    			int st = tag.getInteger("status_" + udid);
						    			if(st == 1) {
						    				a++;
									    	if(a >= 10)
									    		a = 0;
									    	tag.setInteger("crystal_count_del", a);
										data.markDirty();
						    				onBlockActivated(world, x + b + 1, y, z, player, n1, f1, f2, f3);
						    			}
						    		}
						    		if(world.getBlock(x, y, z - b - 1) == BlocksCore.crystal) {
						    			udid = BaseUtils.udid(x, y, z - b - 1);
						    			int st = tag.getInteger("status_" + udid);
						    			if(st == 1) {
						    				a++;
									    	if(a >= 10)
									    		a = 0;
									    	tag.setInteger("crystal_count_del", a);
										data.markDirty();
							    			onBlockActivated(world, x, y, z - b - 1, player, n1, f1, f2, f3);
						    			}
						    		}
						    		if(world.getBlock(x, y, z + b + 1) == BlocksCore.crystal) {
						    			udid = BaseUtils.udid(x, y, z + b + 1);
						    			int st = tag.getInteger("status_" + udid);
						    			if(st == 1) {
						    				a++;
									    	if(a >= 10)
									    		a = 0;
									    	tag.setInteger("crystal_count_del", a);
										data.markDirty();
							    			onBlockActivated(world, x, y, z + b + 1, player, n1, f1, f2, f3);
						    			}
						    		}
						    		
						    		b++;
						    	}
				    		}
					}
				}
			}
		}
		data.markDirty();*/
		return true;
    }

}
