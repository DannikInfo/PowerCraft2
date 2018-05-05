package powercraft.transport;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.Material;
import powercraft.api.item.PC_ItemArmor;
import powercraft.api.registry.PC_LangRegistry.LangEntry;

public class PCtr_ItemArmorStickyBoots extends PC_ItemArmor{
    
	public PCtr_ItemArmorStickyBoots(int id){
        super(ArmorMaterial.IRON, FEET, "stickyboots");
        setArmorTextureFile("slimeboots.png");
    }

	@Override
	public List<LangEntry> getNames(ArrayList<LangEntry> names) {
		names.add(new LangEntry(getUnlocalizedName(), "Sticky Iron Boots"));
        return names;
	}
	
}
