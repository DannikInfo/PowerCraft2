package powercraft.api.hooks;

import powercraft.hooklib.minecraft.HookLoader;
import powercraft.hooklib.minecraft.PrimaryClassTransformer;

public class PC_Hooks extends HookLoader {

	@Override
	public String[] getASMTransformerClass() {
		return new String[]{PrimaryClassTransformer.class.getName()};
	}

	@Override
	public void registerHooks() {
		registerHookContainer("powercraft.storage.PCs_TileEntityChest");
	}
}

/*public static void registerHooks() {
		fixEnderman();
	}

	private static void fixEnderman() {
		boolean[] carriableBlocks = (boolean[]) PC_ReflectHelper.getValue(EntityEnderman.class, EntityEnderman.class, 0,
				boolean[].class);
		// boolean[] newCarriableBlocks = new boolean[Block.blocksList.length];
		// for(int i=0; i<carriableBlocks.length; i++){
		// newCarriableBlocks[i] = carriableBlocks[i];
		// }
		PC_ReflectHelper.setValue(EntityEnderman.class, EntityEnderman.class, 0, carriableBlocks, boolean[].class);
	}*/

//}
