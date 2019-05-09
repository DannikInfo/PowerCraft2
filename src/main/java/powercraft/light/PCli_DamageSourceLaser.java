package powercraft.light;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import powercraft.api.registry.PC_LangRegistry;

public class PCli_DamageSourceLaser extends DamageSource {
	
	private static PCli_DamageSourceLaser instance;
	
	public static DamageSource getDamageSource() {
		if(instance==null)
			instance = new PCli_DamageSourceLaser();
		return instance;
	}
	
	private PCli_DamageSourceLaser(){
		super("laser");
	}
	
}
