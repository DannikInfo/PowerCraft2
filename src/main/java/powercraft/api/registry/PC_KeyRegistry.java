package powercraft.api.registry;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import powercraft.api.annotation.PC_KeyHandler;
import powercraft.launcher.PC_Property;

public final class PC_KeyRegistry {

	//Понять что это за говно и переписать нахер.
	//Первые попытки были не удачными.
	protected static HashMap<EntityPlayer, List<String>> keyPressed = new HashMap<EntityPlayer, List<String>>();
	public static List<Class> handlers;
	public static List<KeyBinding> keyBindings;
	protected static int keyReverse;
	
	public static boolean isPlacingReversed(EntityPlayer player) {
		return isKeyPressed(player, "keyReverse");
	}

	public static boolean isKeyPressed(EntityPlayer player, String key) {
		if (!keyPressed.containsKey(player)) {
			return false;
		}

		List<String> keyList = keyPressed.get(player);
		return keyList.contains(key);
	}

	public static void watchForKey(String name, int key) {
		PC_RegistryServer.getInstance().watchForKey(name, key);
	}

	public static int watchForKey(PC_Property config, String name, int key, String... info) {
		key = config.getInt("key." + name, key, info);
		watchForKey(name, key);
		return key;
	}

	public static void setReverseKey(PC_Property config) {
		keyReverse = watchForKey(config, "keyReverse", 29, "Key for rotate placing");
	}

	@SubscribeEvent
	protected static void onKeyEvent(KeyInputEvent event) {
		/*for(int i = 0; i < handlers.size(); i++) {
			for (Method m : handlers.get(i).getDeclaredMethods()) {
				if (m.isAnnotationPresent(PC_KeyHandler.class)){
					try {
						if(keyBindings.get(i).isPressed())
							m.invoke(handlers.get(i));
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		*/
	}
	
	public PC_KeyRegistry() {
		super();
	}

	public String getLabel() {
		return "PowerCraft";
	}
	
	public void addKey(String name, int key, Class handler) {
		handlers.add(handler);
		keyBindings.add(new KeyBinding(name, key, getLabel()));

		for (int i = 0; i < keyBindings.size(); ++i) {
			ClientRegistry.registerKeyBinding(keyBindings.get(i));
		}
	}

}
