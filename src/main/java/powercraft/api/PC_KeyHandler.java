package powercraft.api;

import java.util.EnumSet;
import java.util.List;

import cpw.mods.fml.client.registry.ClientRegistry;
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

	/*
	 * public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd,
	 * boolean isRepeat) { PC_RegistryClient.keyEvent(kb.getKeyDescription(), true);
	 * }
	 * 
	 * public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
	 * PC_RegistryClient.keyEvent(kb.getKeyDescription(), false); }
	 * 
	 * public EnumSet<TickType> ticks() { return EnumSet.of(TickType.CLIENT,
	 * TickType.RENDER); }
	 */

	public void addKey(String name, int key) {
		keyBindings.add(new KeyBinding(name, key, getLabel()));

		for (int i = 0; i < keyBindings.size(); ++i) {
			ClientRegistry.registerKeyBinding(keyBindings.get(i));
		}
	}

}
