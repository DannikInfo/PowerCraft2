package ru.dannikinfo.powercraft.core.security;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.core.Main;

public class Security extends BlockPane{
	public NBTTagCompound nbt = new NBTTagCompound();
	public boolean isPowered;
	
	public Security() {
		super("glass", "glass_pane_top", Material.glass, false);
		setCreativeTab(Main.tabPowerCraft);
		setHardness(1000F);
		setBlockTextureName("glass");
		setBlockName(Main.MODID + ".security");
		
	}
	
    public int damageDropped(int par1){
        return par1;
    }
	
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack){
        return true;
    }

	public int getRenderType() {
		return this.blockMaterial == Material.glass ? 41 : 18;
	}
	
    @Override
    public int getRenderBlockPass(){
        return 1;
    }
	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity){
		//System.out.println("collided");
    }
}
