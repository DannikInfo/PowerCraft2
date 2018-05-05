package powercraft.api.network;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.world.World;
import powercraft.api.PC_Side;
import powercraft.api.utils.PC_ClientUtils;
import powercraft.launcher.PC_Logger;

public abstract class PC_PacketServerToClient extends PC_PacketServerToClientBase {
	
	@SideOnly(Side.CLIENT)
	protected abstract PC_Packet doAndReply(NetHandlerPlayClient playClient, World world, EntityPlayer player);
	
	@Override
	@SideOnly(Side.CLIENT)
	protected PC_Packet doAndReply(PC_Side side, INetHandler iNetHandler) {
		if(checkSide(side) && iNetHandler instanceof NetHandlerPlayClient && PC_ClientUtils.mc().theWorld!=null && PC_ClientUtils.mc().thePlayer!=null){
			return doAndReply((NetHandlerPlayClient)iNetHandler, PC_ClientUtils.mc().theWorld, PC_ClientUtils.mc().thePlayer);
		}
		return null;
	}

	private static boolean checkSide(PC_Side side){
		if(side!=PC_Side.CLIENT){
			PC_Logger.severe("A server to client packet can't run on server");
			return false;
		}
		return true;
	}
	
}
