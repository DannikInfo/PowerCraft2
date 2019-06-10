package powercraft.machines;

import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Keyboard;

import powercraft.api.gres.PC_GresInventory;
import powercraft.api.gres.PC_GresInventoryPlayer;
import powercraft.api.gres.PC_GresWidget;
import powercraft.api.gres.PC_GresWidget.PC_GresAlign;
import powercraft.api.network.PC_PacketHandler;
import powercraft.api.network.packet.PC_PacketSyncInvTC_pt1;
import powercraft.api.network.packet.PC_PacketSyncPlayerInvTC_pt1;
import powercraft.api.gres.PC_GresWindow;
import powercraft.api.gres.PC_IGresClient;
import powercraft.api.gres.PC_IGresGui;
import powercraft.api.tileentity.PC_TileEntity;

public class PCma_GuiBlockBuilder extends PCma_ContainerBlockBuilder implements PC_IGresClient {

	private EntityPlayer player;

	public PCma_GuiBlockBuilder(EntityPlayer player, PC_TileEntity te, Object[] o) {
		super(player, te, o);
		this.player = player;
	}

	@Override
	public void initGui(PC_IGresGui gui) {
		PC_PacketHandler.sendToServer(new PC_PacketSyncInvTC_pt1(tileEntity, 0));
		PC_PacketHandler.sendToServer(new PC_PacketSyncPlayerInvTC_pt1(tileEntity));
		PC_GresWidget w = new PC_GresWindow("tile.PCma_BlockBlockBuilder.name").setWidthForInventory();

		w.setAlignH(PC_GresAlign.CENTER);

		PC_GresInventory inv = new PC_GresInventory(3, 3);

		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 3; i++) {
				inv.setSlot(i, j, invSlots[i + j * 3]);
			}
		}
		w.add(inv);
		w.add(new PC_GresInventoryPlayer(true));

		gui.add(w);

		w.calcChildPositions();
	}

	@Override
	public void onGuiClosed(PC_IGresGui gui) {
		tileEntity.syncInventory(0, player, 0);
	}

	@Override
	public void actionPerformed(PC_GresWidget widget, PC_IGresGui gui) {
	}

	@Override
	public void onKeyPressed(PC_IGresGui gui, char c, int i) {
		if (i == Keyboard.KEY_RETURN || i == Keyboard.KEY_ESCAPE || i == Keyboard.KEY_E) {
			gui.close();
		}
	}

	@Override
	public void updateTick(PC_IGresGui gui) {
	}

	@Override
	public void updateScreen(PC_IGresGui gui) {
	}

	@Override
	public boolean drawBackground(PC_IGresGui gui, int par1, int par2, float par3) {
		return false;
	}

	@Override
	public void keyChange(String key, Object value) {
	}

}
