package powercraft.api.hooks;

import net.minecraft.entity.monster.EntityEnderman;
import powercraft.api.reflect.PC_ReflectHelper;

public class PC_Hooks {
	
	public static void registerHooks() {
		fixEnderman();
	}
	
	private static void fixEnderman(){
		boolean[] carriableBlocks = (boolean[])PC_ReflectHelper.getValue(EntityEnderman.class, EntityEnderman.class, 0, boolean[].class);
		//boolean[] newCarriableBlocks = new boolean[Block.blocksList.length];
		//for(int i=0; i<carriableBlocks.length; i++){
		//	newCarriableBlocks[i] = carriableBlocks[i];
		//}
		PC_ReflectHelper.setValue(EntityEnderman.class, EntityEnderman.class, 0, carriableBlocks, boolean[].class);
	}
	
}
