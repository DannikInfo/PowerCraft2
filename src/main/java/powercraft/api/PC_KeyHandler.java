package powercraft.api;

import java.util.EnumSet;
import java.util.List;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.settings.KeyBinding;
import powercraft.api.registry.PC_RegistryClient;


public class PC_KeyHandler {

	public static List<KeyBinding> keyBindings;

	public PC_KeyHandler() {
		super();
	}

	public String getLabel() {
		return "PowerCraft";
	}
	
	public void addKey(String name, int key) {
		keyBindings.add(new KeyBinding(name, key, getLabel()));

		for (int i = 0; i < keyBindings.size(); ++i) {
			ClientRegistry.registerKeyBinding(keyBindings.get(i));
		}
	}

}
