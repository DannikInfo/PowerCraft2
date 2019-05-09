package powercraft.launcher;

import java.io.File;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class PC_LauncherClientUtils extends PC_LauncherUtils {

	public static Minecraft mc() {
		return Minecraft.getMinecraft();
	}

	@Override
	protected boolean pIsClient() {
		return true;
	}

	@Override
	protected File pGetMCDirectory() {
		Minecraft mc = null;
		return mc.getMinecraft().mcDataDir;
	}

	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		// Note that if you simply return 'Minecraft.getMinecraft().thePlayer',
		// your packets will not work as expected because you will be getting a
		// client player even when you are on the server!
		// Sounds absurd, but it's true.

		// Solution is to double-check side before returning the player:
		return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx));
	}
}
