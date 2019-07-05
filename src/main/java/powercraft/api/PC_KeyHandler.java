package powercraft.api;

import java.util.EnumSet;
import java.util.List;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.settings.KeyBinding;
import powercraft.api.registry.PC_RegistryClient;


public class PC_KeyHandler{ //check need it remove?

	public static List<KeyBinding> keyBindings;

	public PC_KeyHandler() {
		super();
	}
	
	public void keyDown(EnumSet<TickEvent.Type> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
		PC_RegistryClient.keyEvent(kb.getKeyDescription(), true);
	}

	public void keyUp(EnumSet<TickEvent.Type> types, KeyBinding kb, boolean tickEnd) {
		PC_RegistryClient.keyEvent(kb.getKeyDescription(), false);
	}

	public EnumSet<TickEvent.Type> ticks() {
		return EnumSet.of(TickEvent.Type.CLIENT, TickEvent.Type.RENDER);
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
