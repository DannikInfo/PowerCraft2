package powercraft.api.network;

import net.minecraft.network.INetHandler;
import powercraft.api.PC_Side;
import powercraft.launcher.PC_Logger;

abstract class PC_PacketServerToClientBase extends PC_Packet {
	
	@Override
	protected PC_Packet doAndReply(PC_Side side, INetHandler iNetHandler) {
		PC_Logger.severe("A server to client packet can't run on server");
		return null;
	}
	
}
